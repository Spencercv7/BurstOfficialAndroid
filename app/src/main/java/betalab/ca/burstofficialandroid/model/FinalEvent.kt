package betalab.ca.burstofficialandroid.model

import java.util.*

data class FinalEvent(
    var name: String,
    var startDateO: Calendar,
    var endDateO: Calendar,
    var allDay: Boolean,
    var repeats: Boolean,
    var alert: Boolean)
