package betalab.ca.burstofficialandroid.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import betalab.ca.burstofficialandroid.data.db.Converters
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "events")
data class EventEntry(
    @SerializedName("all-day")
    val allDay: Boolean,
    @SerializedName("attending")
    val attendees: Array<String>,
    @PrimaryKey
    @SerializedName("event-id")
    val eventId: String,
    @SerializedName("host-name")
    val hostName: String,
    @SerializedName("host")
    val host: String,
    @SerializedName("images")
    val images: Array<String>,
    @SerializedName("location")
    val location: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("repeat")
    val repeat: Long,
    @SerializedName("tags")
    val tags: Array<String>,
    @SerializedName("start")
    val start: Long,
    @SerializedName("end")
    val end: Long,
    @SerializedName("attending_count")
    val attendeeCount: Int?,
    @SerializedName("cover-image")
    val coverImage: String?,
    @SerializedName("main-image")
    val mainImage: String?
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventEntry

        if (allDay != other.allDay) return false
        if (!attendees.contentEquals(other.attendees)) return false
        if (eventId != other.eventId) return false
        if (hostName != other.hostName) return false
        if (host != other.host) return false
        if (!images.contentEquals(other.images)) return false
        if (location != other.location) return false
        if (description != other.description) return false
        if (name != other.name) return false
        if (repeat != other.repeat) return false
        if (!tags.contentEquals(other.tags)) return false
        if (start != other.start) return false
        if (end != other.end) return false
        if (attendeeCount != other.attendeeCount) return false
        if (coverImage != other.coverImage) return false
        if (mainImage != other.mainImage) return false
        return true
    }

    override fun hashCode(): Int {
        var result = allDay.hashCode()
        result = 31 * result + attendees.contentHashCode()
        result = 31 * result + eventId.hashCode()
        result = 31 * result + hostName.hashCode()
        result = 31 * result + host.hashCode()
        result = 31 * result + images.contentHashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + repeat.hashCode()
        result = 31 * result + tags.contentHashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + attendeeCount.hashCode()
        result = 31 * result + coverImage.hashCode()
        result = 31 * result + mainImage.hashCode()
        return result
    }
}