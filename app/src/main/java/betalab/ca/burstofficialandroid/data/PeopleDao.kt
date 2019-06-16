package betalab.ca.burstofficialandroid.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry

@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(people: PeopleEntry)

    @Query("select * from people where isOrganization = 1")
    fun getClubs(): LiveData<List<PeopleEntry>>
}