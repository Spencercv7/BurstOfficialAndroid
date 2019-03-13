package betalab.ca.burstofficialandroid

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.activity_school_an.*

class LoginActivity : AppCompatActivity() {
    private val landingNumber = 0
    private val loginNumber = 1
    private val schoolNumber = 2
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
        button_back.setOnClickListener { back() }
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


}
