package betalab.ca.burstofficialandroid.data.repository

import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry

interface PeopleRepository {
    suspend fun getClubs(): LiveData<List<PeopleEntry>>
}