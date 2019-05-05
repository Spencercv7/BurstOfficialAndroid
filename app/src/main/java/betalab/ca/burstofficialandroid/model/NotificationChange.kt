package betalab.ca.burstofficialandroid.model

open class NotificationChange(
    description: String?,
    title: String?,
    date: String?,  //to be replaced with Date object
    time: String?,
    location: String?,

    var original: String?,
    var new: String?
) : Notification(description, title, date, time, location)