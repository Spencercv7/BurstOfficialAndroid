package betalab.ca.burstofficialandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry
import betalab.ca.burstofficialandroid.data.network.BurstApiService
import betalab.ca.burstofficialandroid.data.network.response.EventIdResponse
import betalab.ca.burstofficialandroid.internal.glide.GlideApp
import betalab.ca.burstofficialandroid.ui.activity.NewEventActivity
import betalab.ca.burstofficialandroid.ui.adapter.ClubAdapter
import betalab.ca.burstofficialandroid.ui.adapter.ExploreAdapter
import betalab.ca.burstofficialandroid.ui.viewmodels.EventViewModel
import betalab.ca.burstofficialandroid.ui.viewmodels.EventsViewModelFactory
import betalab.ca.burstofficialandroid.ui.util.NavigationUtils
import betalab.ca.burstofficialandroid.ui.viewmodels.ClubViewModel
import betalab.ca.burstofficialandroid.ui.viewmodels.ClubViewModelFactory
import com.alamkanak.weekview.toCalendar
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.item_explore_main_event_placeholder.view.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class ExploreFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: EventsViewModelFactory by instance()
    private val clubViewmodelFactory: ClubViewModelFactory by instance()
    private val burstApiService: BurstApiService by instance()

    private lateinit var viewModel: EventViewModel
    private lateinit var clubViewModel: ClubViewModel

    companion object {
        fun newInstance(): ExploreFragment {
            return ExploreFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
        clubViewModel = ViewModelProviders.of(this, clubViewmodelFactory).get(ClubViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.explore_fragment_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ClubAdapter()
        recyclerView.hasFixedSize()
        bindUI()
    }

    private fun bindUI() = launch {
        val clubs = clubViewModel.clubs.await()
        clubs.observe(this@ExploreFragment, Observer {
            (recyclerView.adapter as ClubAdapter).updateData(it)
        })
        val featureEvents = burstApiService.getFeaturedEvents(2).await()
        if(featureEvents[0].images.isNotEmpty())
            GlideApp.with(this@ExploreFragment).load(featureEvents[0].images[0]).into(feature_card_1.explore_event_placeholder_image)
        feature_card_1.featureHostName.text = featureEvents[0].hostName
        feature_card_1.featureLocation.text = featureEvents[0].location
        val dateText = DateFormat.format("MMM dd, yyyy", ZonedDateTime.ofInstant(Instant.ofEpochSecond(featureEvents[0].start), ZoneId.systemDefault()).toCalendar()).toString()  +
                "\n" + DateFormat.format("h:mm a", ZonedDateTime.ofInstant(Instant.ofEpochSecond(featureEvents[0].start), ZoneId.systemDefault()).toCalendar()) +
                DateFormat.format("h:mm a", ZonedDateTime.ofInstant(Instant.ofEpochSecond(featureEvents[0].end), ZoneId.systemDefault()).toCalendar())
        feature_card_1.explore_event_date.text = dateText
        feature_card_1.explore_event_title.text = featureEvents[0].name
        explore_list_yours.setOnClickListener {
            val intent = Intent(context!!, NewEventActivity::class.java)
            startActivity(intent)
        }

    }

}

interface OnClickAdapterExplore {
    fun onClickedAdapterExplore(event: EventEntry)
}