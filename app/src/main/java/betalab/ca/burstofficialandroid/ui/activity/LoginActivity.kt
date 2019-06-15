package betalab.ca.burstofficialandroid.ui.activity

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.util.ValidationUtils
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.import_class_calendar.*
import kotlinx.android.synthetic.main.onboarding_calendar.*
import kotlinx.android.synthetic.main.onboarding_interests.*
import kotlinx.android.synthetic.main.onboarding_landing.*
import kotlinx.android.synthetic.main.onboarding_login.*
import kotlinx.android.synthetic.main.onboarding_notifications.*
import kotlinx.android.synthetic.main.onboarding_profile.*
import kotlinx.android.synthetic.main.onboarding_school.*
import android.webkit.JsResult
import android.webkit.WebChromeClient
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import betalab.ca.burstofficialandroid.ui.util.notification.PrefUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import net.fortuna.ical4j.data.CalendarBuilder
import java.io.FileInputStream


class LoginActivity : AppCompatActivity() {
    enum class SCREEN(val value: Int) {
        LANDING(0), LOGIN(1),
        SCHOOL(2), PROFILE(3),
        IMPORT(4), NOTIFICATION(5),
        INTERESTS(6), IMPORT_CLASS_CALENDAR(7);
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get Firebase Authentication
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user != null) {
            // User is signed in
            // startMainActivity()
        }

        setContentView(R.layout.activity_onboarding)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        setScreen(SCREEN.LANDING)
        password_edit_text.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO)
                startMainActivity()
            true
        }
        instantiateListeners()
    }

    private fun setScreen(screen: SCREEN) {
        viewFlipper.displayedChild = screen.value
    }

    private fun instantiateListeners() {
        //landing screen
        get_started_button.setOnClickListener { setScreen(SCREEN.SCHOOL) }
        login_button.setOnClickListener { setScreen(SCREEN.LOGIN) }

        //Login screen
        login_login_button.setOnClickListener { attemptFirebaseSignIn(email_edit_text.editText?.text.toString(), password_edit_text.editText?.text.toString()) }  //open main activity
        back_button_login.setOnClickListener { setScreen(SCREEN.LANDING) } //back to landing

        //activity_school_an screen
        button_back.setOnClickListener { setScreen(SCREEN.LANDING) }   //back to landing
        button_profile.setOnClickListener { setScreen(SCREEN.PROFILE) } //temp will change later


        //profile screen
        calendar_button_profile.setOnClickListener {
            if (validateRegister()) {
                createFirebaseUser()
            }
        }
        location_button_profile.setOnClickListener { setScreen(SCREEN.SCHOOL) }
//        name_register.editText?.setOnEditorActionListener { v, keyCode, event ->
//
//            return@setOnEditorActionListener !isUsernameValid(name_register)
//
//        }
        //Picker for profile image
        profile_pic_chooser.setOnClickListener {
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropMenuCropButtonTitle("CONFIRM")
                .start(this)
        }

        name_register.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                name_register.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
//        email_register.editText?.setOnEditorActionListener { v, keyCode, event ->
//
//                return@setOnEditorActionListener !isEmailValid(email_register)
//
//        }
        email_register.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                email_register.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        password_register.editText?.setOnEditorActionListener { _, _, _ ->
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            if (validateRegister())
                setScreen(SCREEN.IMPORT)
            return@setOnEditorActionListener true
//            if(validateRegister()) {
//                setScreen(SCREEN.IMPORT)
//                return@setOnEditorActionListener true
//            }
//            return@setOnEditorActionListener true
        }
        password_register.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                password_register.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })


        //Calendar screen
        calendar_back.setOnClickListener { setScreen(SCREEN.PROFILE) } //Go back to profile
        calendar_skip.setOnClickListener { setScreen(SCREEN.NOTIFICATION) } //forward to notification screen
        import_class_calendar_button.setOnClickListener { importClassCalendar() }
        import_class_back_arrow.setOnClickListener { setScreen(SCREEN.IMPORT) }

        //notification screen
        skip_button_notifications.setOnClickListener { setScreen(SCREEN.INTERESTS) }
        location_button_notifications.setOnClickListener { setScreen(SCREEN.IMPORT) }

        //Interests screen
        confirm_interests_button.setOnClickListener { startMainActivity() }

    }







    //Get cropped image picked for profile pictures
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                PrefUtil.setProfilePicUrl(resultUri.toString(), this@LoginActivity) //set preference to url of profile picture
                Glide.with(this)
                    .load(resultUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profile_pic_chooser)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }






    private fun createFirebaseUser() {
        auth.createUserWithEmailAndPassword(email_register.editText?.text.toString(), password_register.editText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "createUserWithEmail:success")
                    setScreen(SCREEN.IMPORT) //Go to import calendar screen
                } else {
                    // If sign in fails, display a message to the user. TODO: Make message more descriptive change from toast
                    Log.w("Failure", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
         }
    }

    private fun attemptFirebaseSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    startMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    // TODO: Change from toast to better display of message
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    //task.exception
                    Toast.makeText(baseContext, "Authentication failed: " + task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }

    private fun validateRegister(): Boolean {
        name_register.error = ValidationUtils.isUsernameValid(name_register.editText?.text.toString())
        email_register.error = ValidationUtils.isEmailValid(email_register.editText?.text.toString())
        password_register.error = ValidationUtils.isPasswordValid(password_register.editText?.text.toString())
        return name_register.error == null && email_register.error == null && password_register.error == null
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }



    //IMPORT CLASS CALENDAR HANDLING
    //TODO: MOVE TO DIFFERENT LOCATION FOR REUSE LATER ON IF WANTED?
    private fun importClassCalendar() {
        if (PrefUtil.getCalUrl(this@LoginActivity).isNullOrBlank()) {
            setScreen(SCREEN.IMPORT_CLASS_CALENDAR)
            setUpWebview()
            import_class_webview.loadUrl("https://my.queensu.ca/software-centre")
        } else {
            if(checkWritePermission())
                downloadFile()
            else
                requestWritePermission()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebview() {
        import_class_webview.settings.javaScriptEnabled = true
        import_class_webview.addJavascriptInterface(MyJavaScriptInterface(), "HtmlViewer")

        import_class_webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                import_class_webview.loadUrl(
                    "javascript:alert(HtmlViewer.showHTML" +     //read the html of the page
                            "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'));"
                )

                if (url == "https://my.queensu.ca/software-centre") {   //If the webview gets to the software page exit the screen
                    setScreen(SCREEN.IMPORT)
                    import_class_calendar_button.visibility =
                        View.GONE
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.e("URL", url)

                if (url!!.startsWith("https://login.queensu.ca/idp/profile/SAML2/")) { //attempt to hide other pages that try to be loaded
                    import_class_webview.visibility = View.VISIBLE
                    import_calendar_progress.visibility = View.GONE
                } else {
                    import_class_webview.visibility = View.GONE
                    import_calendar_progress.visibility = View.VISIBLE
                }


                if (!(url.contains("https://login.queensu.ca/idp/profile/SAML2/") || url.contains("https://my.queensu.ca/software-centre") ||
                            url.contains("https://my.queensu.ca/Shibboleth.sso/SAML2"))
                ) {
                    import_class_webview.loadUrl("https://my.queensu.ca/software-centre") //if user goes off track redirects
                }

                super.onPageStarted(view, url, favicon)
            }
        }

        import_class_webview.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                Log.e("LogTag", message)
                if (message!!.contains("https://mytimetable.queensu.ca/timetable")) {
                    PrefUtil.setCalUrl(message, this@LoginActivity)
                    if(checkWritePermission())
                        downloadFile()
                    else
                        requestWritePermission()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
                result!!.confirm()
                return true
            }
        }
        import_class_webview.visibility = View.VISIBLE
    }

    private val EXTERNAL_PERMISSION_READ = 1
    private val EXTERNAL_PERMISSION_WRITE = 2
    private val onDownloadComplete = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "file downloaded" + intent?.extras.toString(), Toast.LENGTH_SHORT).show()
            if(checkReadPermission())
                readCalendar()
            else
                requestReadPermission()
        }

    }


    @RequiresPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun downloadFile() {
        val url = PrefUtil.getCalUrl(this@LoginActivity)
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setDescription("Calendar ICS Download").setTitle("calendar.ics").setAllowedOverRoaming(true)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "calendar.ics")
        request.setVisibleInDownloadsUi(true).setMimeType("ics")
        downloadManager.enqueue(request)
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        fun showHTML(html: String): String {
            if (html.contains("https://mytimetable.queensu.ca/timetable")) {    //stop if html contains this url
                return html.substring(
                    html.indexOf("https://mytimetable.queensu.ca/timetable"),  //substring starts here
                    html.indexOf(".</p>\n" + "  <p>Visit the ITS")     //ends right before this
                )
            }
            return "not found"
        }
    }

    private fun checkReadPermission(): Boolean {
        val readResult = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return readResult == PackageManager.PERMISSION_GRANTED
    }
    private fun checkWritePermission(): Boolean {
        val writeResult = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return writeResult == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(
                this,
                "External Storage permission allows us to store your calendar. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                EXTERNAL_PERMISSION_READ
            )
        }
    }
    private fun requestWritePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this,
                "External Storage permission allows us to store your calendar. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                EXTERNAL_PERMISSION_WRITE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            EXTERNAL_PERMISSION_READ -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted,")
                if(checkReadPermission())
                    readCalendar()
            } else {
                Log.e("value", "Permission Denied")
            }
            EXTERNAL_PERMISSION_WRITE -> downloadFile()
        }
    }

    @RequiresPermission(allOf = [android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE])
    private fun readCalendar() {
        val test = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).list()
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "/calendar.ics"
        )
        val result = FileInputStream(file).use {

            val calendar = CalendarBuilder().build(it)
            calendar.components
        }
        Log.e("test", result.toString())
    }
}



