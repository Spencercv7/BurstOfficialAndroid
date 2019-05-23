package betalab.ca.burstofficialandroid.data.repository

import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry

interface EventsRepository {
    suspend fun getEvents(): LiveData<List<EventEntry>>

}