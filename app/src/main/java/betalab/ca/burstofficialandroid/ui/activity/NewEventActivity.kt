package betalab.ca.burstofficialandroid.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import betalab.ca.burstofficialandroid.model.FinalEvent
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.activity_new_event.*
import java.text.SimpleDateFormat
import java.util.*

class NewEventActivity : AppCompatActivity() {

    private var startDateO: Calendar = Calendar.getInstance()
    private var endDateO: Calendar = Calendar.getInstance()
    private lateinit var dpdStart: DatePickerDialog
    private lateinit var dpdEnd: DatePickerDialog
    private var startTimeO: Calendar = Calendar.getInstance()
    private var endTimeO: Calendar = Calendar.getInstance()
    private lateinit var dpdTimeStart: TimePickerDialog
    private lateinit var dpdTimeEnd: TimePickerDialog
    private lateinit var finalEvent : FinalEvent
    private lateinit var name : String
    private var allDay: Boolean = false
    private var repeats: Boolean = false
    private var alert: Boolean = false

    override fun onStart() {
        super.onStart()

        setContentView(R.layout.activity_new_event)
        setSupportActionBar(findViewById(R.id.new_event_toolBar))

        val simpleDate = SimpleDateFormat(getString(R.string.simple_date_format_str), Locale.getDefault()) // Formats for Date and Time
        val simpleTime = SimpleDateFormat(getString(R.string.time_format_str), Locale.getDefault())

        val startCal = Calendar.getInstance()
        val year = startCal.get(Calendar.YEAR)
        val month = startCal.get(Calendar.MONTH)
        val day = startCal.get(Calendar.DAY_OF_MONTH)

        start_date.setOnClickListener {
            dpdStart = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                    startDateO = dateFromInput(mYear, mMonth, mDay)
                    start_date.text = simpleDate.format(startDateO.time)
                }, year, month, day
            )
            dpdStart.show()
        }

        end_date.setOnClickListener {
            dpdEnd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                    endDateO = dateFromInput(mYear, mMonth, mDay)
                    end_date.text = simpleDate.format(endDateO.time)
                }, year, month, day
            )
            dpdEnd.show()
        }

        start_time.setOnClickListener {
            dpdTimeStart = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    startTimeO.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    startTimeO.set(Calendar.MINUTE, minute)
                    start_time.text = simpleTime.format(startTimeO.time)
                },
                startTimeO.get(Calendar.HOUR_OF_DAY), startTimeO.get(Calendar.MINUTE), false
            )
            dpdTimeStart.show()
        }

        end_time.setOnClickListener {
            dpdTimeEnd = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    endTimeO.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    endTimeO.set(Calendar.MINUTE, minute)
                    end_time.text = simpleTime.format(endTimeO.time)
                },
                endTimeO.get(Calendar.HOUR_OF_DAY), endTimeO.get(Calendar.MINUTE), false
            )
            dpdTimeEnd.show()
        }

        new_event_save_button.setOnClickListener {
            saveButtonClick()
        }

        all_day_switch.setOnClickListener {
            if (all_day_switch.isChecked) {
                allDay = true
                start_time.setText(R.string.all_day_st)
                end_time.text = getString(R.string.all_day_st)
                end_card.visibility = View.INVISIBLE
            } else if (!all_day_switch.isChecked) {
                start_time.setText(R.string.enter_time_hint)
                end_time.text = getString(R.string.enter_time_hint)
                end_card.visibility = View.VISIBLE
            }
        }

        new_event_repeats_check.setOnClickListener {
            if (new_event_repeats_check.isChecked) {
                repeats = true
            } else if (!new_event_repeats_check.isChecked) {
                repeats = false
            }
        }

        new_event_alert_check.setOnClickListener {
            if (new_event_alert_check.isChecked) {
                alert = true
            } else if (!new_event_repeats_check.isChecked) {
                alert = false
            }

        }
        new_event_back_button.setOnClickListener {
            finish()
        }

        new_event_back_x.setOnClickListener {
            finish()
        }
    }

    private fun dateFromInput(mYear: Int, mMonth: Int, mDay: Int): Calendar {
        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(mYear, mMonth, mDay)
        return tempCal
    }

    /**
     * Assigns the times and dates to one start and end calendar event. Then creates a FinalEvent Which holds all the information for the new event.
     */
    private fun saveButtonClick() {
        startDateO.set(Calendar.HOUR_OF_DAY, startTimeO.get(Calendar.HOUR_OF_DAY))
        startDateO.set(Calendar.MINUTE, startTimeO.get(Calendar.MINUTE))
        endDateO.set(Calendar.MINUTE, endTimeO.get(Calendar.MINUTE))
        endDateO.set(Calendar.HOUR_OF_DAY, endTimeO.get(Calendar.HOUR_OF_DAY))
        name = event_name_edit_text.editText!!.text.toString()
        finalEvent =
            FinalEvent(name, startDateO, endDateO, allDay, repeats, alert)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

