package betalab.ca.burstofficialandroid.data.network

import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.network.response.EventsResponse


interface EventsDataSource {
    val downloadedEvents: LiveData<EventsResponse>

    suspend fun fetchEvents()
}