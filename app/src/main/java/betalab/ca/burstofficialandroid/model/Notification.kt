package betalab.ca.burstofficialandroid.model

data class Notification(
    var description: String?,
    var title: String?,
    var date: String?,  //to be replaced with Date object
    var time: String?,
    var location: String?)