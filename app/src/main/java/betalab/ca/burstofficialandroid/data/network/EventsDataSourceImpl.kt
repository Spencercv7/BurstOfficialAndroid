package betalab.ca.burstofficialandroid.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.network.response.EventsResponse
import betalab.ca.burstofficialandroid.internal.NoConnectivityException

class EventsDataSourceImpl(private val burstApiService: BurstApiService) : EventsDataSource {
    private val _downloadedEvents = MutableLiveData<List<EventsResponse>>()
    override val downloadedEvents: LiveData<List<EventsResponse>>
        get() = _downloadedEvents

    override suspend fun fetchEvents() {
        try {
            Log.e("CMON", "test")
            val fetchedCurrentWeather = burstApiService
                .getEventsAsync()
                .await()
            fetchedCurrentWeather.forEach { Log.e("CMON", it.toString()) }
            _downloadedEvents.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}