package betalab.ca.burstofficialandroid.data.network.response


import com.google.gson.annotations.SerializedName

data class EventIdResponse(
    @SerializedName("attending_count")
    val attendingCount: Int,
    @SerializedName("event-id")
    val eventId: String,
    @SerializedName("_id")
    val id: Id
)