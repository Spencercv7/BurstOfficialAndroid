package betalab.ca.burstofficialandroid.data.network.response

import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import betalab.ca.burstofficialandroid.data.db.Converters
import betalab.ca.burstofficialandroid.data.db.entity.Attendees
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.db.entity.Images
import betalab.ca.burstofficialandroid.data.db.entity.Tags
import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("all-day")
    val allDay: Boolean,
    @SerializedName("attending")
    val attendees: List<String>,
    @SerializedName("event-id")
    val eventId: String,
    @SerializedName("host")
    val host: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("repeat")
    val repeat: Boolean,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("time")
    val time: String
) {
    fun toEntry(): EventEntry {
        return EventEntry(allDay,Attendees(attendees), eventId,host,Images(images), location, name, repeat, Tags(tags), time)
    }
}