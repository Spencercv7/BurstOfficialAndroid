package betalab.ca.burstofficialandroid.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import betalab.ca.burstofficialandroid.data.network.response.EventsResponse
import betalab.ca.burstofficialandroid.internal.NoConnectivityException

class EventsDataSourceImpl(private val burstApiService: BurstApiService) : EventsDataSource {
    private val _downloadedEvents = MutableLiveData<EventsResponse>()
    override val downloadedEvents: LiveData<EventsResponse>
        get() = _downloadedEvents

    override suspend fun fetchEvents() {
        try {
            val fetchedCurrentWeather = burstApiService
                .getEventsAsync()
                .await()
            _downloadedEvents.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}