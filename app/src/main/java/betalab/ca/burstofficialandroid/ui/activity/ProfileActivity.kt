package betalab.ca.burstofficialandroid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.Card
import betalab.ca.burstofficialandroid.model.ScheduleCard
import betalab.ca.burstofficialandroid.ui.adapter.ExploreAdapter
import betalab.ca.burstofficialandroid.ui.adapter.HomeEventCardAdapter
import betalab.ca.burstofficialandroid.ui.adapter.HomeScheduleAdapter
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val dataSetSchedule = arrayOf(
            ScheduleCard("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCard("APSC 221", "BioSci Aud", "4:30PM-5:30PM"),
            ScheduleCard("CISC 124", "Ellis Aud", "10:30AM-11:30AM"),
            ScheduleCard("CISC 101", "Jeffery Hall 101", "12:30PM-1PM"),
            ScheduleCard("APSC 221", "BioSci Aud", "4:30PM-5:30PM")
        )
        profile_horz_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        profile_vert_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        profile_horz_recycler.adapter = ExploreAdapter()
        profile_vert_recycler.adapter = HomeScheduleAdapter(dataSetSchedule)
        profile_vert_recycler.hasFixedSize()
        profile_horz_recycler.hasFixedSize()
        profile_close_button.setOnClickListener { onBackPressed() }
    }
}


