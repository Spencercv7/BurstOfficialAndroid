package betalab.ca.burstofficialandroid.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.EventCardData
import betalab.ca.burstofficialandroid.model.ScheduleCardData
import betalab.ca.burstofficialandroid.ui.activity.EventActivity
import betalab.ca.burstofficialandroid.ui.adapter.HomeEventCardAdapter
import betalab.ca.burstofficialandroid.ui.adapter.HomeScheduleAdapter

class HomeFragment: Fragment(), OnClickAdapterHome{

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var cardRecycler: RecyclerView
    private lateinit var vertRecycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardRecycler = view.findViewById(R.id.card_recycler) as RecyclerView
        vertRecycler = view.findViewById(R.id.vert_card_recycler) as RecyclerView

        val dataSetEvent = listOf(
            EventCardData(
                "Finance 101",
                "Queen's Finance Association\nConference",
                "February 2, 2019",
                "4:30PM - 6:30PM",
                "Leonard Hall"
            ),
            EventCardData(
                "Finance 305",
                "Queen's Finance Association\nConference",
                "February 2, 2019",
                "4:30PM - 6:30PM",
                "Leonard Hall"
            )
        )
        val dataSetSchedule = listOf(
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM"),
            ScheduleCardData("CISC 124", "Ellis Aud", "10:30AM-11:30AM"),
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM")
        )

        cardRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        vertRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        cardRecycler.adapter = HomeEventCardAdapter(dataSetEvent) { event : EventCardData -> onClickedAdapterHome(event)}
        vertRecycler.adapter = HomeScheduleAdapter(dataSetSchedule)

        vertRecycler.hasFixedSize(); cardRecycler.hasFixedSize()

    }

   // Event is the card that was selected by the user. This gives us access to all that cards information.
   override fun onClickedAdapterHome(event: EventCardData) {
        val intent = Intent(context, EventActivity::class.java)
        startActivity(intent)
    }

}

interface OnClickAdapterHome {
    fun onClickedAdapterHome(event: EventCardData)
}