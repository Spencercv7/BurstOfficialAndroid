package betalab.ca.burstofficialandroid.data.network.response

import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("result")
    val events: List<EventEntry>
)