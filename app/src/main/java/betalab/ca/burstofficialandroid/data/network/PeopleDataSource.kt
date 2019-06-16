package betalab.ca.burstofficialandroid.data.network

import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry
import betalab.ca.burstofficialandroid.data.network.response.PeopleResponse

interface PeopleDataSource {
    val downloadedClubs: LiveData<List<PeopleResponse>>

    suspend fun fetchClubs()
}