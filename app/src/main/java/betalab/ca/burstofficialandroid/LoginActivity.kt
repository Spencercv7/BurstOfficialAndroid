package betalab.ca.burstofficialandroid

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_login.*
//import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.android.synthetic.main.activity_onboarding.*

class LoginActivity : AppCompatActivity() {
    private val landingNumber = 0
    private val loginNumber = 1
    private val schoolNumber = 2
    private val profileNumber = 3
    private val importNumber = 4
    private val notificationNumber = 5
    private val interestsNumber = 6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        viewFlipper.displayedChild = landingNumber
        password_edit_text.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO)
                loginToApp()
            true
        }
        instantiateListeners()
    }
    private fun instantiateListeners() {
        //landing screen
        get_started_button.setOnClickListener { register() }
        login_button.setOnClickListener { launchLoginScreen() }

        //Login screen
        login_login_button.setOnClickListener { loginToApp() }
        back_button_login.setOnClickListener { back() }

        //activity_school_an screen
//        button_back.setOnClickListener { back() }
//        button_profile.setOnClickListener { profile() } //temp will change later
//
//        profile_back.setOnClickListener { register() }
//        profile_next_calendar.setOnClickListener { calendar() }

        //activity_calendar screen
        calendar_back.setOnClickListener { back() }
        calendar_skip.setOnClickListener { notifications() }

        //notification_skip.setOnClickListener { interests() }
        //notifications_back.setOnClickListener { calendar() }


    }
    private fun back() {
        viewFlipper.displayedChild = landingNumber
    }
    private fun loginToApp() {
        Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show()
    }
    private fun register() {
        viewFlipper.displayedChild = schoolNumber
    }
    private fun launchLoginScreen() {
        viewFlipper.displayedChild = loginNumber
    }
    private fun profile() {
        viewFlipper.displayedChild = profileNumber
    }
    private fun calendar() {
        viewFlipper.displayedChild = importNumber
    }
    private fun notifications() {
        viewFlipper.displayedChild = notificationNumber
    }
    private fun interests() {
        viewFlipper.displayedChild = interestsNumber
    }


}
