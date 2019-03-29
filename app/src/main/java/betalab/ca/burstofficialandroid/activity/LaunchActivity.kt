package betalab.ca.burstofficialandroid.activity

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LoginActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()

    }
}