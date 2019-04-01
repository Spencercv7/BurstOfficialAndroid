package betalab.ca.burstofficialandroid.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.activity_school_an.*
import kotlinx.android.synthetic.main.notif_an.*
import kotlinx.android.synthetic.main.profile_picture_an.*

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
        login_login_button.setOnClickListener { loginToApp() }  //open main activity
        back_button_login.setOnClickListener { back() } //back to landing

        //activity_school_an screen
        button_back.setOnClickListener { back() }   //back to landing
        button_profile.setOnClickListener { profile() } //temp will change later

        skip_button_notifications.setOnClickListener { launchLoginScreen()
        Toast.makeText(this, "Should go to interests but skips for now", Toast.LENGTH_LONG).show()}
        //ABOVE should go to interests but for now skip after "creating account" and goes to login so you
        //login with newly created account
        location_button_notifications.setOnClickListener { calendar() }

        calendar_button_profile.setOnClickListener { calendar() }
        location_button_profile.setOnClickListener { register() }

        calendar_back.setOnClickListener { profile() }
        calendar_skip.setOnClickListener { notifications() }


    }
    private fun back() {
        viewFlipper.displayedChild = landingNumber
    }
    private fun loginToApp() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
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
