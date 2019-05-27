package betalab.ca.burstofficialandroid.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import betalab.ca.burstofficialandroid.data.db.Converters
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "events")
data class EventEntry(
    @SerializedName("all-day")
    val allDay: Boolean,
    @SerializedName("attending")
    val attendees: Attendees,
    @PrimaryKey
    @SerializedName("event-id")
    val eventId: String,
    @SerializedName("host")
    val host: String,
    @SerializedName("images")
    val images: Images,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("repeat")
    val repeat: Boolean,
    @SerializedName("tags")
    val tags: Tags,
    @SerializedName("time")
    val time: String
)