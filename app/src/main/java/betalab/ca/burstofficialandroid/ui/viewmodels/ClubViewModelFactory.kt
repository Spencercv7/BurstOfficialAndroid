package betalab.ca.burstofficialandroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import betalab.ca.burstofficialandroid.data.repository.PeopleRepository

class ClubViewModelFactory(
    private val peopleRepository: PeopleRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClubViewModel(peopleRepository) as T
    }
}