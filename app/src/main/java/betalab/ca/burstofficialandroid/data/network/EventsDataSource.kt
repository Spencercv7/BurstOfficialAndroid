package betalab.ca.burstofficialandroid.data.network

import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry


interface EventsDataSource {
    val downloadedEvents: LiveData<List<EventEntry>>

    suspend fun fetchEvents()
}