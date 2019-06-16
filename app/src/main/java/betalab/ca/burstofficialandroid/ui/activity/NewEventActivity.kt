package betalab.ca.burstofficialandroid.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.data.network.BurstApiService
import betalab.ca.burstofficialandroid.data.network.response.ServerReponse
import com.alamkanak.weekview.toCalendar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_event.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewEventActivity : AppCompatActivity(), KodeinAware {

    private var startDateO: ZonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusHours(1).withMinute(0)
    override val kodein by closestKodein()
    private val burstApiService: BurstApiService by instance()
    private var endDateO: ZonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusHours(4).withMinute(0)


    private val dpdStart by lazy {
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                startDateO = startDateO.withYear(mYear).withMonth(mMonth).withDayOfMonth(mDay)
                start_date.text = DateFormat.format("MMM, dd, yyyy", startDateO.toCalendar())
            }, startDateO.year, startDateO.monthValue, startDateO.dayOfMonth
        )
    }
    private val dpdEnd by lazy {
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                endDateO = endDateO.withYear(mYear).withMonth(mMonth).withDayOfMonth(mDay)
                end_date.text = DateFormat.format("MMM, dd, yyyy", endDateO.toCalendar())
            }, endDateO.year, endDateO.monthValue, endDateO.dayOfMonth
        )
    }
    private val dpdTimeStart by lazy {
        TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                startDateO = startDateO.withHour(hourOfDay).withMinute(minute)
                start_time.text = DateFormat.format("h:mm a", startDateO.toCalendar())
            }, startDateO.hour, startDateO.minute, false
        )
    }
    private val dpdTimeEnd by lazy {
        TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                endDateO = endDateO.withHour(hourOfDay).withMinute(minute)
                end_time.text = DateFormat.format("h:mm a", endDateO.toCalendar())
            }, endDateO.hour, endDateO.minute, false
        )
    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_new_event)
        setSupportActionBar(findViewById(R.id.new_event_toolBar))
        start_date.text = DateFormat.format("MMM, dd, yyyy", startDateO.toCalendar())
        start_time.text = DateFormat.format("h:mm a", startDateO.toCalendar())
        end_date.text = DateFormat.format("MMM, dd, yyyy", endDateO.toCalendar())
        end_time.text = DateFormat.format("h:mm a", endDateO.toCalendar())
        // Formats for Date and Time
        start_date.setOnClickListener {
            dpdStart.show()
        }
        end_date.setOnClickListener {
            dpdEnd.show()
        }
        start_time.setOnClickListener {
            dpdTimeStart.show()
        }
        end_time.setOnClickListener {
            dpdTimeEnd.show()
        }
        new_event_save_button.setOnClickListener {
            saveButtonClick()
        }
        all_day_switch.setOnCheckedChangeListener { _, isChecked ->
            all_day_group.visibility = if (isChecked) Group.INVISIBLE else Group.VISIBLE
            all_day_group.requestLayout()
        }

        new_event_back_button.setOnClickListener {
            finish()
        }

        new_event_back_x.setOnClickListener {
            finish()
        }
    }


    /**
     * Assigns the times and dates to one start and end calendar event. Then creates a FinalEvent Which holds all the information for the new event.
     */
    private fun saveButtonClick() {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)!!.addOnCompleteListener {
            burstApiService.createEventAsync(
                token = it.result!!.token!!,
                eventName = event_name_edit_text.editText!!.text.toString(),
                location= event_location_edit_text.editText!!.text.toString(),
                description = "temp empty description",
                startTimeEpoch = startDateO.toEpochSecond(),
                endTimeEpoch = endDateO.toEpochSecond(),
                allDay = isAllDay(),
                repeat = 0,
                images = listOf("https://img.freepik.com/free-photo/tulips-bouquet-pink-background-with-copyspace_24972-271.jpg?size=626&ext=jpg",
                    "https://images.unsplash.com/photo-1500322969630-a26ab6eb64cc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"),
                mainImage = "https://www.gamespew.com/wp-content/uploads/2019/02/Portalcake-696x391.jpg",
                coverImage = "https://images.unsplash.com/photo-1500322969630-a26ab6eb64cc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"

            ).enqueue(object : Callback<ServerReponse> {
                override fun onFailure(call: Call<ServerReponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error: check internet connection", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ServerReponse>, response: Response<ServerReponse>) {
                    Log.e("test", response.toString())
                    Toast.makeText(applicationContext, "Event created", Toast.LENGTH_SHORT).show()
                    finish()
                }

            })
        }


    }

    private fun isRepeating(): Boolean {
        return new_event_repeats_check.isChecked
    }

    private fun isAlert(): Boolean {
        return new_event_alert_check.isChecked
    }

    private fun isAllDay(): Boolean {
        return all_day_switch.isChecked
    }

}

