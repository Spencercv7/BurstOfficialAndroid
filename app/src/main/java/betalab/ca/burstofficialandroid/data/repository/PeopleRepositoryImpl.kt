package betalab.ca.burstofficialandroid.data.repository

import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.PeopleDao
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry
import betalab.ca.burstofficialandroid.data.network.PeopleDataSource
import betalab.ca.burstofficialandroid.data.network.response.PeopleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeopleRepositoryImpl (private val peopleDao: PeopleDao, private val eventsDataSource:PeopleDataSource): PeopleRepository {

    init {
        eventsDataSource.apply {
            downloadedClubs.observeForever {
                persistFetchedClubs(it)
            }
        }
    }
    private fun persistFetchedClubs(events: List<PeopleResponse>) {
        GlobalScope.launch(Dispatchers.IO) {
            events.forEach {peopleDao.upsert(it.toEntry()) }

        }
    }
    override suspend fun getClubs(): LiveData<List<PeopleEntry>> {
        return withContext(Dispatchers.IO) {
            initClubs()
            return@withContext peopleDao.getClubs()
        }
    }
    private suspend fun initClubs() {
        if (isFetchClubsNeeded())
            fetchEvents()
    }
    private suspend fun fetchEvents() {
        eventsDataSource.fetchClubs()
    }
    private fun isFetchClubsNeeded(): Boolean {
        return true
    }

}