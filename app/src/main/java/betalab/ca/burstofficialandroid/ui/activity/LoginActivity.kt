package betalab.ca.burstofficialandroid.ui.activity

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
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


class LoginActivity : AppCompatActivity() {
    enum class SCREEN(val value: Int) {
        LANDING(0), LOGIN(1),
        SCHOOL(2), PROFILE(3),
        IMPORT(4), NOTIFICATION(5),
        INTERESTS(6), IMPORT_CLASS_CALENDAR(7);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        Toast.makeText(this, "Starts at import screen for testing purposes", Toast.LENGTH_LONG).show()
        setScreen(SCREEN.LANDING)
        password_edit_text.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO)
                loginToApp()
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
        login_login_button.setOnClickListener { loginToApp() }  //open main activity
        back_button_login.setOnClickListener { setScreen(SCREEN.LANDING) } //back to landing

        //activity_school_an screen
        button_back.setOnClickListener { setScreen(SCREEN.LANDING) }   //back to landing
        button_profile.setOnClickListener { setScreen(SCREEN.PROFILE) } //temp will change later


        //profile screen
        calendar_button_profile.setOnClickListener {
            if (validateRegister())
                setScreen(SCREEN.IMPORT)
        }
        location_button_profile.setOnClickListener { setScreen(SCREEN.SCHOOL) }
//        name_register.editText?.setOnEditorActionListener { v, keyCode, event ->
//
//            return@setOnEditorActionListener !isUsernameValid(name_register)
//
//        }
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
        confirm_interests_button.setOnClickListener { loginToApp() }

    }

    private fun loginToApp() {
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




    //IMPORT CLASS CALENDAR HANDLING
    private fun importClassCalendar() {
        setScreen(SCREEN.IMPORT_CLASS_CALENDAR)
        setUpWebview()
        import_class_webview.loadUrl("https://my.queensu.ca/software-centre")
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
                    import_class_calendar_button.visibility = View.GONE //TODO: MAKE BETTER METHOD TO PREVENT MULTIPLE ATTEMPTS
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.e("URL", url)

                if (url!!.startsWith("https://login.queensu.ca/idp/profile/SAML2/")){ //attempt to hide other pages that try to be loaded
                    import_class_webview.visibility = View.VISIBLE
                    import_calendar_progress.visibility = View.GONE
                }
                else{
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
                    downloadFile(message)
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
                result!!.confirm()
                return true
            }
        }
        import_class_webview.visibility = View.VISIBLE
    }

    fun downloadFile(url: String?) {
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setDescription("Calendar ICS Download").setTitle("Calendar Download")
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "calendar")
        request.setVisibleInDownloadsUi(true)
        downloadManager.enqueue(request)  //TODO: TAKE TO INTERNAL STORAGE?
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
}


