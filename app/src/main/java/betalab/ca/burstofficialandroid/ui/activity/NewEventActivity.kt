package betalab.ca.burstofficialandroid.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import betalab.ca.burstofficialandroid.model.FinalEvent
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.activity_new_event.*
import java.text.SimpleDateFormat
import java.util.*

class NewEventActivity : AppCompatActivity() {

    private var startDateO: Calendar = Calendar.getInstance().also {
        it.add(Calendar.HOUR, 1)
        it.set(Calendar.MINUTE, 0)
    }
    private var endDateO: Calendar = (startDateO.clone() as Calendar).also { it.add(Calendar.HOUR, 3) }
    private val simpleDate by lazy { SimpleDateFormat(getString(R.string.simple_date_format_str), Locale.getDefault()) }
    private val simpleTime by lazy { SimpleDateFormat(getString(R.string.time_format_str), Locale.getDefault()) }
    private val dpdStart by lazy {
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                startDateO = dateFromInput(mYear, mMonth, mDay)
                start_date.text = simpleDate.format(startDateO.time)
            }, startDateO.get(Calendar.YEAR), startDateO.get(Calendar.MONTH), startDateO.get(Calendar.MONTH)
        )
    }
    private val dpdEnd by lazy {
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                endDateO = dateFromInput(mYear, mMonth, mDay)
                end_date.text = simpleDate.format(endDateO.time)
            }, endDateO.get(Calendar.YEAR), endDateO.get(Calendar.MONTH), endDateO.get(Calendar.MONTH)
        )
    }
    private val dpdTimeStart by lazy {
        TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                startDateO.set(Calendar.HOUR_OF_DAY, hourOfDay)
                startDateO.set(Calendar.MINUTE, minute)
                start_time.text = simpleTime.format(startDateO.time)
            }, startDateO.get(Calendar.HOUR_OF_DAY), startDateO.get(Calendar.MINUTE), false
        )
    }
    private val dpdTimeEnd by lazy {
        TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                endDateO.set(Calendar.HOUR_OF_DAY, hourOfDay)
                endDateO.set(Calendar.MINUTE, minute)
                end_time.text = simpleTime.format(endDateO.time)
            }, endDateO.get(Calendar.HOUR_OF_DAY), endDateO.get(Calendar.MINUTE), false
        )
    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_new_event)
        setSupportActionBar(findViewById(R.id.new_event_toolBar))
        start_date.text = simpleDate.format(startDateO.time)
        start_time.text = simpleTime.format(startDateO.time)
        end_date.text = simpleDate.format(endDateO.time)
        end_time.text = simpleTime.format(endDateO.time)
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

    private fun dateFromInput(mYear: Int, mMonth: Int, mDay: Int): Calendar {
        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(mYear, mMonth, mDay)
        return tempCal
    }

    /**
     * Assigns the times and dates to one start and end calendar event. Then creates a FinalEvent Which holds all the information for the new event.
     */
    private fun saveButtonClick() {
        @Suppress("UNUSED_VARIABLE") val finalEvent = FinalEvent(
            event_name_edit_text.editText!!.text.toString(),
            startDateO, endDateO,
            isAllDay(), isRepeating(), isAlert()
        )
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

