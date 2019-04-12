package betalab.ca.burstofficialandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import betalab.ca.burstofficialandroid.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.onboarding_calendar.*
import kotlinx.android.synthetic.main.onboarding_interests.*
import kotlinx.android.synthetic.main.onboarding_landing.*
import kotlinx.android.synthetic.main.onboarding_login.*
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.onboarding_school.*
import kotlinx.android.synthetic.main.onboarding_notifications.*
import kotlinx.android.synthetic.main.onboarding_profile.*
import android.view.inputmethod.InputMethodManager




class LoginActivity : AppCompatActivity() {
    enum class SCREEN(val value: Int) {
        LANDING(0), LOGIN(1),
        SCHOOL(2), PROFILE(3),
        IMPORT(4), NOTIFICATION(5),
        INTERESTS(6);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
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
        name_register.editText?.addTextChangedListener(object: TextWatcher {
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
        email_register.editText?.addTextChangedListener(object: TextWatcher {
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
        password_register.editText?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                password_register.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })


        //Calendar screen
        calendar_back.setOnClickListener { setScreen(SCREEN.PROFILE) } //Go back to profile
        calendar_skip.setOnClickListener { setScreen(SCREEN.NOTIFICATION) } //forward to notification screen

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

    private fun isUsernameValid(view: TextInputLayout): Boolean {
        return view.editText?.run {
            view.error = when {
                this.text.isEmpty() -> "Empty username"
                this.text.length > 33 -> "Username must be below 32 characters"
                !isUsernameAvailable(this.text.toString()) -> "Username already taken"
                else -> null
            }
            view.error.isNullOrBlank()
        } ?: false
    }

    private fun isEmailValid(view: TextInputLayout): Boolean {
        return view.editText?.run {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches())
                view.error = "Invalid email address"
            view.error.isNullOrBlank()

        } ?: false
    }

    private fun isPasswordValid(view: TextInputLayout): Boolean {
        return view.editText?.run {
            view.error = when {
                this.text.isEmpty() -> "Password empty"
                this.text.length < 8 -> "Password must be at least 8 characters"
                !hasLowerCase(this.text.toString()) -> "Password must contain one lowercase letter"
                !hasUpperCase(this.text.toString()) -> "Password must contain one uppercase letter"
                !hasNumber(this.text.toString()) -> "Password must contain one number"
                resources.getStringArray(R.array.common_passwords).contains(this.text.toString()) ->
                    "Username found in common passwords"
                this.text.length > 127 -> "Username must be less than 128 characters"
                else -> null
            }
            view.error.isNullOrBlank()
        } ?: false
    }

    private fun isUsernameAvailable(@Suppress("UNUSED_PARAMETER") name: String): Boolean {
        return true
    }

    private fun hasLowerCase(s: String): Boolean = s.filter { c -> c.isLowerCase() }.count() > 0
    private fun hasUpperCase(s: String): Boolean = s.filter { c -> c.isUpperCase() }.count() > 0
    private fun hasNumber(s: String): Boolean = s.contains(Regex("\\d"))
    private fun validateRegister(): Boolean {
        return isUsernameValid(name_register) and isEmailValid(email_register) and isPasswordValid(password_register)
    }
}


