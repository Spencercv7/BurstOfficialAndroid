package betalab.ca.burstofficialandroid.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import betalab.ca.burstofficialandroid.data.EventsDao
import betalab.ca.burstofficialandroid.data.PeopleDao
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry

@Database(
    entities = [PeopleEntry::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PeopleDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao

    companion object {
        @Volatile private var instance: PeopleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PeopleDatabase::class.java, "people.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}