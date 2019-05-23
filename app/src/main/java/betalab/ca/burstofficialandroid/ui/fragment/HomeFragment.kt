package betalab.ca.burstofficialandroid.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.Card
import betalab.ca.burstofficialandroid.model.ScheduleCard
import betalab.ca.burstofficialandroid.ui.adapter.HomeEventCardAdapter
import betalab.ca.burstofficialandroid.ui.adapter.HomeScheduleAdapter
import betalab.ca.burstofficialandroid.ui.events.EventViewModel
import betalab.ca.burstofficialandroid.ui.events.EventsViewModelFactory
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class HomeFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: EventsViewModelFactory by instance()

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
                Log.e("huh", events.toString())
            })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSetEvent = arrayOf(
            Card(
                "Finance 101",
                "Queen's Finance Association\nConference",
                "February 2, 2019",
                "4:30PM - 6:30PM",
                "Leonard Hall"
            ),
            Card(
                "Finance 305",
                "Queen's Finance Association\nConference",
                "February 2, 2019",
                "4:30PM - 6:30PM",
                "Leonard Hall"
            )
        )
        val dataSetSchedule = arrayOf(
            ScheduleCard("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCard("APSC 221", "BioSci Aud", "4:30PM-5:30PM"),
            ScheduleCard("CISC 124", "Ellis Aud", "10:30AM-11:30AM"),
            ScheduleCard("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCard("APSC 221", "BioSci Aud", "4:30PM-5:30PM")
        )
        cardRecycler = view.findViewById(R.id.card_recycler) as RecyclerView
        vertRecycler = view.findViewById(R.id.vert_card_recycler) as RecyclerView
        cardRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        vertRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        cardRecycler.adapter = HomeEventCardAdapter(dataSetEvent)
        vertRecycler.adapter = HomeScheduleAdapter(dataSetSchedule)
        vertRecycler.hasFixedSize()
        cardRecycler.hasFixedSize()


    }

}