package betalab.ca.burstofficialandroid.model

import com.alamkanak.weekview.WeekViewEvent

import com.alamkanak.weekview.WeekViewDisplayable
import java.util.*


data class CalendarItem(
    private val id: Long, val title: String, private val startTime: Calendar, private val endTime: Calendar,
    private val location: String, private val color: Int, private val isAllDay: Boolean = false
) : WeekViewDisplayable<CalendarItem> {

    override fun toWeekViewEvent(): WeekViewEvent<CalendarItem> {
        // Note: It's important to pass "this" as the last argument to WeekViewEvent's constructor.
        // This way, the EventClickListener can return this object in its onEventClick() method.
        return WeekViewEvent.Builder<CalendarItem>().setId(id).setTitle(title).setStartTime(startTime).setEndTime(endTime)
            .setLocation(location).setStyle(WeekViewEvent.Style.Builder().setBackgroundColor(color).build()).setAllDay(isAllDay).setData(this)
            .build()
    }

}