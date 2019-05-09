package betalab.ca.burstofficialandroid.database

import betalab.ca.burstofficialandroid.model.CalendarItem
import com.alamkanak.weekview.WeekViewDisplayable

import java.util.Calendar

interface EventsDatabase {

    fun getEventsInRange(startDate: Calendar, endDate: Calendar): List<WeekViewDisplayable<CalendarItem>>

}