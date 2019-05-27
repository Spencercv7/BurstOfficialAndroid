package betalab.ca.burstofficialandroid.data.db

import androidx.room.TypeConverter
import betalab.ca.burstofficialandroid.data.db.entity.Attendees
import betalab.ca.burstofficialandroid.data.db.entity.Images
import betalab.ca.burstofficialandroid.data.db.entity.Tags
import com.google.gson.Gson
import java.util.*
import java.util.Arrays.asList


class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toAttendee(value: String): Attendees {
            val langs = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
            return Attendees(langs)
        }

        @TypeConverter
        @JvmStatic
        fun fromAttendee(attendees: Attendees): String {
            return attendees.attendees?.joinToString(separator = ",") ?: ""
        }
        @TypeConverter
        @JvmStatic
        fun toImages(value: String): Images {
            val images = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
            return Images(images)
        }

        @TypeConverter
        @JvmStatic
        fun fromImages(images: Images): String {
            return images.images?.joinToString(separator = ",") ?: ""
        }
        @TypeConverter
        @JvmStatic
        fun toTags(value: String): Tags {
            val tags = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
            return Tags(tags)
        }

        @TypeConverter
        @JvmStatic
        fun fromTags(tags: Tags): String {
            return tags.tags?.joinToString(separator = ",") ?: ""
        }
    }
}