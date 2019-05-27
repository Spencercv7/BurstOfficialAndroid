package betalab.ca.burstofficialandroid.ui.events


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import betalab.ca.burstofficialandroid.data.repository.EventsRepository


class EventsViewModelFactory(
    private val eventsRepository: EventsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventViewModel(eventsRepository) as T
    }
}