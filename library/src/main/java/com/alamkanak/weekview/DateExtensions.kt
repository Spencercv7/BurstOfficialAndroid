@file:JvmName("DateUtils")
package com.alamkanak.weekview

import android.content.Context
import android.text.format.DateFormat
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.SATURDAY
import java.util.Calendar.SUNDAY

fun Calendar.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(timeInMillis).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Calendar.toZonedDateTime(): ZonedDateTime {
    val instant = Instant.ofEpochMilli(timeInMillis)
    return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
}

fun LocalDate.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = toEpochDay() * Constants.DAY_IN_MILLIS
    return calendar
}

fun ZonedDateTime.toCalendar(): Calendar {
    val timeZone = TimeZone.getTimeZone(zone.id)
    val calendar = Calendar.getInstance(timeZone)
    calendar.timeInMillis = toEpochSecond() * 1_000
    return calendar
}

fun getDateRange(daysSinceToday: Int, size: Int): List<LocalDate> {
    return (daysSinceToday..size).map { today().plusDays(it - 1L) }
}

fun now(): ZonedDateTime = ZonedDateTime.now()

fun today(): LocalDate = LocalDate.now()

fun firstDayOfYear(): LocalDate = LocalDate.of(today().year, 1, 1)

fun getDefaultDateFormat(numberOfDays: Int): SimpleDateFormat {
    return when (numberOfDays) {
        7 -> SimpleDateFormat("EEEEE M/dd", Locale.getDefault()) // display the first character
        1 -> SimpleDateFormat("EEEE M/dd", Locale.getDefault()) // display full weekday
        else -> SimpleDateFormat("EEE M/dd", Locale.getDefault()) // display first three characters
    }
}

fun getDefaultTimeFormat(context: Context): SimpleDateFormat {
    val format = if (DateFormat.is24HourFormat(context)) "HH:mm" else "hh a"
    return SimpleDateFormat(format, Locale.getDefault())
}

internal fun ZonedDateTime.withTimeAtStartOfDay(): ZonedDateTime = withTimeAtStartOfPeriod(0)

internal fun ZonedDateTime.withTimeAtEndOfDay(): ZonedDateTime = withTimeAtEndOfPeriod(24)

internal val LocalDate.isToday: Boolean
    get() = isEqual(today())

internal val LocalDate.isWeekend: Boolean
    get() = dayOfWeek.value == SATURDAY || dayOfWeek.value == SUNDAY

internal val LocalDate.isBeforeToday: Boolean
    get() = isBefore(today())

internal fun ZonedDateTime.withTimeAtStartOfPeriod(hour: Int): ZonedDateTime {
    return withHour(hour).withMinute(0).withSecond(0).withNano(0)
}

internal fun ZonedDateTime.withTimeAtEndOfPeriod(hour: Int): ZonedDateTime {
    return withHour(hour - 1).withMinute(59).withSecond(59).withNano(999_999)
}

internal fun ZonedDateTime.isSameDate(other: ZonedDateTime): Boolean {
    return toLocalDate().isEqual(other.toLocalDate())
}

/**
 * Checks if this date is at the start of the next day after startTime.
 * For example, if the start date was January the 1st and startDate was January the 2nd at 00:00,
 * this method would return true.
 * @param startDate The start date of the event
 * @return Whether or not this date is at the start of the day after startDate
 */
internal fun ZonedDateTime.isAtStartOfNextDay(startDate: ZonedDateTime): Boolean {
    if (isEqual(withTimeAtStartOfDay())) {
        val thisCalendar = minusNanos(1)
        return thisCalendar.isSameDate(startDate)
    }
    return false
}

val LocalDate.daysFromToday: Int
    get() = today().until(this).days
