package betalab.ca.burstofficialandroid.model

open class NotificationBroadcast(
    description: String?,
    title: String?,
    date: String?,  //to be replaced with Date object
    time: String?,
    location: String?,

    //Broadcast
    var broadcast: String? = null
) : Notification(description, title, date, time, location)