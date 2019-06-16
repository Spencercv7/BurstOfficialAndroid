package betalab.ca.burstofficialandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.widget.Toast
import betalab.ca.burstofficialandroid.data.network.BurstApiService
import betalab.ca.burstofficialandroid.data.network.response.ServerReponse
import com.google.firebase.auth.FirebaseAuth
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaunchActivity : ScopedActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val burstApiService:BurstApiService by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user != null) {
            burstApiService.isEmailRegistered(user.email!!).enqueue(object : Callback<ServerReponse> {
                override fun onFailure(call: Call<ServerReponse>, t: Throwable) {
                    Toast.makeText(this@LaunchActivity, "Registration check failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ServerReponse>, response: Response<ServerReponse>) {
                    if (response.body()!!.success) {
                        startMainActivity()
                    } else {
                        val intent = Intent(this@LaunchActivity, LoginActivity::class.java).apply {
                            addFlags(FLAG_ACTIVITY_NEW_TASK)
                        }
                        startActivity(intent)
                        finish()
                    }
                }

            })
        } else {
            val intent = Intent(this, LoginActivity::class.java).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()
        }
    }
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }
}