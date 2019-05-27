package betalab.ca.burstofficialandroid.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import betalab.ca.burstofficialandroid.data.EventsDao
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry


@Database(
    entities = [EventEntry::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDao

    companion object {
        @Volatile private var instance: EventsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                EventsDatabase::class.java, "events.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}