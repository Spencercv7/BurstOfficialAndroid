package betalab.ca.burstofficialandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.network.EventsDataSource
import betalab.ca.burstofficialandroid.model.EventCardData
import betalab.ca.burstofficialandroid.model.ScheduleCardData
import betalab.ca.burstofficialandroid.ui.adapter.HomeEventCardAdapter
import betalab.ca.burstofficialandroid.ui.adapter.HomeScheduleAdapter
import betalab.ca.burstofficialandroid.ui.viewmodels.EventViewModel
import betalab.ca.burstofficialandroid.ui.viewmodels.EventsViewModelFactory
import betalab.ca.burstofficialandroid.ui.util.NavigationUtils
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class HomeFragment : ScopedFragment(), KodeinAware, OnClickAdapterHome {

    override val kodein by closestKodein()
    private val viewModelFactory: EventsViewModelFactory by instance()
    private val eventsDataSource: EventsDataSource by instance()

    private lateinit var viewModel: EventViewModel

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var cardRecycler: RecyclerView
    private lateinit var vertRecycler: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
        bindUI()
    }
    private fun bindUI() = launch {
        val currentEvents = viewModel.events.await()
        currentEvents.observe(this@HomeFragment, Observer { events ->
            (cardRecycler.adapter as HomeEventCardAdapter).updateData(events)
            })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardRecycler = view.findViewById(R.id.card_recycler) as RecyclerView
        vertRecycler = view.findViewById(R.id.vert_card_recycler) as RecyclerView

        val dataSetSchedule = listOf(
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM"),
            ScheduleCardData("CISC 124", "Ellis Aud", "10:30AM-11:30AM"),
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM")
        )

        cardRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        vertRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        cardRecycler.adapter = HomeEventCardAdapter { event : EventEntry -> onClickedAdapterHome(event) }
        vertRecycler.adapter = HomeScheduleAdapter(dataSetSchedule)

        vertRecycler.hasFixedSize(); cardRecycler.hasFixedSize()

    }

   // Event is the card that was selected by the user. This gives us access to all that cards information.
   override fun onClickedAdapterHome(event: EventEntry) {
        NavigationUtils.sendToEvent(context!!, event)
    }

    override fun onResume() {
        super.onResume()
        launch {
            eventsDataSource.fetchEvents()
        }
    }

}

interface OnClickAdapterHome {
    fun onClickedAdapterHome(event: EventEntry)
}