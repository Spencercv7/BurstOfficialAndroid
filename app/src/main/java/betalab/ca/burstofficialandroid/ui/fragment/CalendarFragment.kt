package betalab.ca.burstofficialandroid.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.activity.NewEventActivity
import kotlinx.android.synthetic.main.fragment_calendar.*
import betalab.ca.burstofficialandroid.database.EventsDatabase
import betalab.ca.burstofficialandroid.database.FakeEventsDatabase
import betalab.ca.burstofficialandroid.model.CalendarItem
import com.alamkanak.weekview.*
import java.util.*


class CalendarFragment : Fragment() {

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }

    private var animating = false
    private lateinit var  mDatabase: EventsDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDatabase = FakeEventsDatabase(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_calendar,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        @Suppress("UNCHECKED_CAST")
        (weekView as WeekView<CalendarItem>).setMonthChangeListener { startDate, endDate ->
            mDatabase.getEventsInRange(startDate,endDate)
        }
        weekView.eventMarginHorizontal = 4
        instantiateListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun ifNotAnimating(toExecute: () -> Unit) {
        if (!animating) toExecute()
    }

    //region animations
    private fun circleReveal() {
        animating = true
        ViewAnimationUtils.createCircularReveal(
            revealed_card,
            button_card_layout.right,
            button_card_layout.bottom,
            0f,
            Math.hypot(button_card_layout.width.toDouble(), button_card_layout.width.toDouble()).toFloat()
        ).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    animating = false
                }
            })
            fab_import.hide()
            start()
            fab_import.elevation = 0f
            revealed_card.visibility = View.VISIBLE
        }
    }

    private fun circularHide() {
        animating = true
        ViewAnimationUtils.createCircularReveal(
            revealed_card,
            button_card_layout.width,
            button_card_layout.height,
            Math.hypot(button_card_layout.width.toDouble(), button_card_layout.width.toDouble()).toFloat(),
            0f
        ).apply {
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    revealed_card.visibility = View.INVISIBLE
                    animating = false
                }
            })
            fab_import.show()
            start()
            fab_import.elevation = 0f
        }
    }

    //endregion animations
    private fun instantiateListeners() {
        fab_import.setOnClickListener {
            ifNotAnimating { circleReveal() }
        }
        fab_in_menu.setOnClickListener {
            ifNotAnimating { circularHide() }
        }

        newEvent_button.setOnClickListener {
            val intent = Intent(activity, NewEventActivity::class.java)//should be new activity just temp
            startActivity(intent)
        }
        import_cal_button.setOnClickListener {
            Toast.makeText(activity, "Launching Import Activity", Toast.LENGTH_SHORT).show()
        }
    }
}