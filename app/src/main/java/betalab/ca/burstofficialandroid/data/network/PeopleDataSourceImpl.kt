package betalab.ca.burstofficialandroid.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry
import betalab.ca.burstofficialandroid.data.network.response.PeopleResponse
import betalab.ca.burstofficialandroid.internal.NoConnectivityException

class PeopleDataSourceImpl(private val burstApiService: BurstApiService) : PeopleDataSource {
    private val _downloadedClubs = MutableLiveData<List<PeopleResponse>>()
    override val downloadedClubs: LiveData<List<PeopleResponse>>
        get() = _downloadedClubs

    override suspend fun fetchClubs() {
        try {
            val fetchedCurrentWeather = burstApiService
                .getClubsAsync()
                .await()
            _downloadedClubs.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}