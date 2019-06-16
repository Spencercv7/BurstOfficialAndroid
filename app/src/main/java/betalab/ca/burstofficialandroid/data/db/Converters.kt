package betalab.ca.burstofficialandroid.data.db

import androidx.room.TypeConverter
import betalab.ca.burstofficialandroid.data.db.entity.*
import com.google.gson.Gson
import java.util.*
import java.util.Arrays.asList


class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toStringSerializable(value: String): Array<String> {
            val tags = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
            return tags.toTypedArray()
        }

        @TypeConverter
        @JvmStatic
        fun fromStringArray(tags: Array<String>): String {
            return tags.joinToString(separator = ",")
        }
    }
}