package betalab.ca.burstofficialandroid.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import betalab.ca.burstofficialandroid.data.EventsDao
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.network.EventsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsRepositoryImpl (private val eventsDao: EventsDao, private val eventsDataSource:EventsDataSource): EventsRepository {
    override fun getEventById(id: String): EventEntry {
        return eventsDao.getEventById(id)
    }

    init {
        eventsDataSource.apply {
            downloadedEvents.observeForever {
                persistFetchedEvents(it)
            }
        }
    }
    private fun persistFetchedEvents(events: List<EventEntry>) {
        GlobalScope.launch(Dispatchers.IO) {
            events.forEach {eventsDao.upsert(it) }

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