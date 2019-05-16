package betalab.ca.burstofficialandroid.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.util.ValidationUtils
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.fragment_calendar.*
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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


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
        setScreen(SCREEN.IMPORT) //TODO: SWITCH BACK TO LANDING
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
        password_register.editText?.setOnEditorActionListener { v, keyCode, event ->
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
        import_class_calendar_button.setOnClickListener { importClassCalendar()}
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
    @SuppressLint("SetJavaScriptEnabled")
    private fun importClassCalendar() {
        setScreen(SCREEN.IMPORT_CLASS_CALENDAR)
        import_class_webview.settings.javaScriptEnabled = true
        import_class_webview.addJavascriptInterface(MyJavaScriptInterface(this), "HtmlViewer")

        import_class_webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                import_class_webview.loadUrl("javascript:HtmlViewer.showHTML" +     //read the html of the page
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")

                if (url == "https://my.queensu.ca/software-centre" ) {   //If the webview gets to the software page exit the screen
                    setScreen(SCREEN.IMPORT)
                }
            }
        }
        import_class_webview.loadUrl("https://my.queensu.ca/software-centre")
    }



    class MyJavaScriptInterface(private val ctx: Context) {

        @JavascriptInterface
        fun showHTML(html: String) {
            if (html.contains("https://mytimetable.queensu.ca/timetable")) {    //stop if html contains this url
                val icsURL = html.substring(
                    html.indexOf("https://mytimetable.queensu.ca/timetable"),  //substring starts here
                    html.indexOf(".</p>\n" + "  <p>Visit the ITS")     //ends right before this
                )
                Log.e("ICS URL", icsURL)  //TODO: FOR THE LOVE OF GOODNESS LET ME TAKE THIS VALUE SOMEHOW
                Toast.makeText(ctx, icsURL, Toast.LENGTH_LONG).show()
            }
        }
    }
}


