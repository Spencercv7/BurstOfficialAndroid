package betalab.ca.burstofficialandroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import betalab.ca.burstofficialandroid.data.repository.PeopleRepository
import betalab.ca.burstofficialandroid.internal.lazyDeferred

class ClubViewModel(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    val clubs by lazyDeferred {
        peopleRepository.getClubs()
    }

}