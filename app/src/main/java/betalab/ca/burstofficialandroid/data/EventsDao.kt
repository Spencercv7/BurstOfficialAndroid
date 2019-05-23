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

    @Query("select * from events order by date")
    fun getEvents(): LiveData<List<EventEntry>>

}