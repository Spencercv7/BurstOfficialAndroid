package betalab.ca.burstofficialandroid.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.internal.NoConnectivityException

class EventsDataSourceImpl(private val burstApiService: BurstApiService) : EventsDataSource {
    private val _downloadedEvents = MutableLiveData<List<EventEntry>>()
    override val downloadedEvents: LiveData<List<EventEntry>>
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