package betalab.ca.burstofficialandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.onboarding_calendar.*
import kotlinx.android.synthetic.main.onboarding_interests.*
import kotlinx.android.synthetic.main.onboarding_landing.*
import kotlinx.android.synthetic.main.onboarding_login.*
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.onboarding_school.*
import kotlinx.android.synthetic.main.onboarding_notifications.*
import kotlinx.android.synthetic.main.onboarding_profile.*

class LoginActivity : AppCompatActivity() {
    enum class SCREEN(val value:Int) {
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
        calendar_button_profile.setOnClickListener { setScreen(SCREEN.IMPORT) }
        location_button_profile.setOnClickListener { setScreen(SCREEN.SCHOOL) }

        //
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


}
