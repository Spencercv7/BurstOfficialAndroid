package betalab.ca.burstofficialandroid.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry
import betalab.ca.burstofficialandroid.internal.glide.GlideApp
import betalab.ca.burstofficialandroid.model.ScheduleCardData
import betalab.ca.burstofficialandroid.ui.adapter.ExploreAdapter
import betalab.ca.burstofficialandroid.ui.adapter.HomeScheduleAdapter
import betalab.ca.burstofficialandroid.ui.viewmodels.EventViewModel
import betalab.ca.burstofficialandroid.ui.viewmodels.EventsViewModelFactory
import betalab.ca.burstofficialandroid.ui.fragment.OnClickAdapterExplore
import betalab.ca.burstofficialandroid.ui.util.NavigationUtils
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class ProfileActivity : ScopedActivity(), KodeinAware, OnClickAdapterExplore {
    override val kodein by closestKodein()
    private val viewModelFactory: EventsViewModelFactory by instance()

    private lateinit var viewModel: EventViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val profile: PeopleEntry = intent.getSerializableExtra("person") as PeopleEntry
        GlideApp.with(this).load(profile.avatar).into(profile_picture)
        profile_name.text = profile.name
        profile_school.text = profile.program + " @"+profile.school
        follow_button.setOnClickListener { if((it as MaterialButton).text == "FOLLOW") "FOLLOWING" else "FOLLOW"}
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
        bindUI()
        val dataSetSchedule = listOf(
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM"),
            ScheduleCardData("CISC 124", "Ellis Aud", "10:30AM-11:30AM"),
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM")
        )



        profile_horz_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        profile_vert_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        profile_horz_recycler.adapter = ExploreAdapter{event: EventEntry -> onClickedAdapterExplore(event)}
        profile_vert_recycler.adapter = HomeScheduleAdapter(dataSetSchedule)
        profile_vert_recycler.hasFixedSize()
        profile_horz_recycler.hasFixedSize()
        profile_close_button.setOnClickListener { onBackPressed() }
    }
    private fun bindUI() = launch {
        val currentEvents = viewModel.events.await()
        currentEvents.observe(this@ProfileActivity, Observer { events ->
            (profile_horz_recycler.adapter as ExploreAdapter).updateData(events)
        })
    }

    override fun onClickedAdapterExplore(event: EventEntry) {
        NavigationUtils.sendToEvent(this, event)
    }
}


