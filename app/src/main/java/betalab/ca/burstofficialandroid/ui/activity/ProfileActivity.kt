package betalab.ca.burstofficialandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.EventLog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.ExploreHorizontalCardData
import betalab.ca.burstofficialandroid.model.ScheduleCardData
import betalab.ca.burstofficialandroid.ui.adapter.ExploreAdapter
import betalab.ca.burstofficialandroid.ui.adapter.HomeScheduleAdapter
import betalab.ca.burstofficialandroid.ui.fragment.OnClickAdapterExplore
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity(), OnClickAdapterExplore {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val dataSetSchedule = listOf(
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM"),
            ScheduleCardData("CISC 124", "Ellis Aud", "10:30AM-11:30AM"),
            ScheduleCardData("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCardData("APSC 221", "BioSci Aud", "4:30PM-5:30PM")
        )

        val dataSet = listOf(ExploreHorizontalCardData("Card One"), ExploreHorizontalCardData("Card Two"), ExploreHorizontalCardData("Card Three"))

        profile_horz_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        profile_vert_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        profile_horz_recycler.adapter = ExploreAdapter(dataSet) {event: ExploreHorizontalCardData -> onClickedAdapterExplore(event)}
        profile_vert_recycler.adapter = HomeScheduleAdapter(dataSetSchedule)
        profile_vert_recycler.hasFixedSize()
        profile_horz_recycler.hasFixedSize()
        profile_close_button.setOnClickListener { onBackPressed() }
    }

    override fun onClickedAdapterExplore(event: ExploreHorizontalCardData) {
        val intent = Intent(this, EventActivity::class.java)
        startActivity(intent)
    }
}


