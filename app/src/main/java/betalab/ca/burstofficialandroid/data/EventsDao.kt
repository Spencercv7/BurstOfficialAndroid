package betalab.ca.burstofficialandroid.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: EventEntry)

    @Query("select * from events WHERE `end` > :currentTime order by start")
    fun getEvents(currentTime: Long = System.currentTimeMillis()/1000): LiveData<List<EventEntry>>
    @Query("select * from events where eventId = :eventId LIMIT 1")
    fun getEventById(eventId: String): EventEntry
    @Query("select * from events where eventId IN (:eventId)")
    fun getEventsById(eventId: List<String>): EventEntry
}