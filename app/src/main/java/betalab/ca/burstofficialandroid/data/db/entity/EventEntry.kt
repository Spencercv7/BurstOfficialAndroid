package betalab.ca.burstofficialandroid.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "events")
data class EventEntry(
    @PrimaryKey
    @SerializedName("event-id")
    val event_id: String,
    @SerializedName("time")
    val date: String
)