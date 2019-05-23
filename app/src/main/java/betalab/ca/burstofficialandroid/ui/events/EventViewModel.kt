package betalab.ca.burstofficialandroid.ui.events

import androidx.lifecycle.ViewModel
import betalab.ca.burstofficialandroid.data.repository.EventsRepository
import betalab.ca.burstofficialandroid.internal.lazyDeferred

class EventViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    val events by lazyDeferred {
        eventsRepository.getEvents()
    }

}