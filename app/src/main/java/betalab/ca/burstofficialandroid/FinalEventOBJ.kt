package betalab.ca.burstofficialandroid

import java.util.*

data class FinalEventOBJ(
    var name: String,
    var startDateO: Calendar,
    var endDateO: Calendar,
    var allDay: Boolean,
    var repeats: Boolean,
    var alert: Boolean)
