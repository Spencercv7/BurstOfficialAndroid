package betalab.ca.burstofficialandroid

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.new_event.*
import java.text.SimpleDateFormat
import java.util.*

class NewEvent : AppCompatActivity() {

    private var startDateO: Calendar = Calendar.getInstance()
    private var endDateO: Calendar = Calendar.getInstance()
    private lateinit var dpdStart: DatePickerDialog
    private lateinit var dpdEnd: DatePickerDialog
    private var startTimeO: Calendar = Calendar.getInstance()
    private var endTimeO: Calendar = Calendar.getInstance()
    private lateinit var dpdTimeStart: TimePickerDialog
    private lateinit var dpdTimeEnd: TimePickerDialog
    private lateinit var saveButton: MaterialButton
    private lateinit var allDayToggleButton: SwitchCompat
    private lateinit var alertCheck: CheckBox
    private lateinit var repCheck: CheckBox
    private lateinit var finalEvent : FinalEventOBJ
    private lateinit var nameTextEdit : TextInputLayout
    private lateinit var name : String
    private lateinit var endsCard : MaterialCardView
    private var allDay: Boolean = false
    private var repeats: Boolean = false
    private var alert: Boolean = false

    @SuppressLint("SimpleDateFormat")
    override fun onStart() {
        super.onStart()
        setContentView(R.layout.new_event)
        setSupportActionBar(findViewById(R.id.new_event_toolBar))

        val simpleDate = SimpleDateFormat(getString(R.string.simple_date_format_str)) // Formats for Date and Time
        val simpleTime = SimpleDateFormat(getString(R.string.time_format_str))

        val startDate: TextView = findViewById(R.id.start_date)
        val endDate: TextView = findViewById(R.id.end_date)
        val startTime: TextView = findViewById(R.id.start_time)
        val endTime: TextView = findViewById(R.id.end_time)

        val startCal = Calendar.getInstance()
        val year = startCal.get(Calendar.YEAR)
        val month = startCal.get(Calendar.MONTH)
        val day = startCal.get(Calendar.DAY_OF_MONTH)

        allDayToggleButton = findViewById(R.id.all_day_switch)
        alertCheck = findViewById(R.id.new_event_alert_check)
        repCheck = findViewById(R.id.new_event_repeats_check)
        saveButton = findViewById(R.id.new_event_save_button)
        nameTextEdit = findViewById(R.id.event_name_edit_text)
        endsCard = findViewById(R.id.end_card)

        startDate.setOnClickListener {
            dpdStart = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                    startDateO = dateFromInput(mYear, mMonth, mDay)
                    start_date.text = simpleDate.format(startDateO.time)
                }, year, month, day
            )
            dpdStart.show()
        }

        endDate.setOnClickListener {
            dpdEnd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                    endDateO = dateFromInput(mYear, mMonth, mDay)
                    end_date.text = simpleDate.format(endDateO.time)
                }, year, month, day
            )
            dpdEnd.show()
        }

        startTime.setOnClickListener {
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

        endTime.setOnClickListener {
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

        saveButton.setOnClickListener {
            saveButtonClick()
        }

        allDayToggleButton.setOnClickListener {
            if (allDayToggleButton.isChecked) {
                allDay = true
                start_time.setText(R.string.all_day_st)
                end_time.text = getString(R.string.all_day_st)
                endsCard.visibility = View.INVISIBLE
            } else if (!allDayToggleButton.isChecked) {
                start_time.setText(R.string.enter_time_hint)
                end_time.text = getString(R.string.enter_time_hint)
                endsCard.visibility = View.VISIBLE
            }
        }

        repCheck.setOnClickListener {
            if (repCheck.isChecked) {
                repeats = true
            } else if (!repCheck.isChecked) {
                repeats = false
            }
        }

        alertCheck.setOnClickListener {
            if (alertCheck.isChecked) {
                alert = true
            } else if (!repCheck.isChecked) {
                alert = false
            }

        }
    }

    private fun dateFromInput(mYear: Int, mMonth: Int, mDay: Int): Calendar {
        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(mYear, mMonth, mDay)
        return tempCal
    }

    /**
     * Assigns the times and dates to one start and end calendar event. Then creates a FinalEventOBJ Which holds all the information for the new event.
     */
    @SuppressLint("SimpleDateFormat")
    private fun saveButtonClick() {
        startDateO.set(Calendar.HOUR_OF_DAY, startTimeO.get(Calendar.HOUR_OF_DAY))
        startDateO.set(Calendar.MINUTE, startTimeO.get(Calendar.MINUTE))
        endDateO.set(Calendar.MINUTE, endTimeO.get(Calendar.MINUTE))
        endDateO.set(Calendar.HOUR_OF_DAY, endTimeO.get(Calendar.HOUR_OF_DAY))
        name = nameTextEdit.editText!!.text.toString()
        finalEvent = FinalEventOBJ(name, startDateO, endDateO, allDay, repeats, alert)
    }

}

