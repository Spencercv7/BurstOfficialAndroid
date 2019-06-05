package betalab.ca.burstofficialandroid.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.EventsDao
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.network.EventsDataSource
import betalab.ca.burstofficialandroid.data.network.response.EventsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsRepositoryImpl (private val eventsDao: EventsDao, private val eventsDataSource:EventsDataSource): EventsRepository {
    init {
        eventsDataSource.apply {
            downloadedEvents.observeForever {
                persistFetchedEvents(it)
            }
        }
    }
    private fun persistFetchedEvents(events: List<EventsResponse>) {
        GlobalScope.launch(Dispatchers.IO) {
            events.forEach {eventsDao.upsert(it.toEntry()) }

        }
    }
    override suspend fun getEvents(): LiveData<List<EventEntry>> {
        return withContext(Dispatchers.IO) {
            initEvents()
            return@withContext eventsDao.getEvents()
        }
    }
    private suspend fun initEvents() {
        if (isFetchEventsNeeded())
            fetchEvents()
    }
    private suspend fun fetchEvents() {
        eventsDataSource.fetchEvents()
    }
    private fun isFetchEventsNeeded(): Boolean {
        return true
    }

}