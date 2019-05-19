package betalab.ca.burstofficialandroid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.adapter.ExploreAdapter
import kotlinx.android.synthetic.main.activity_event_page.*


class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_page)
        event_gallery_rec.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        event_gallery_rec.adapter = ExploreAdapter()
        event_gallery_rec.hasFixedSize()
        event_similar_rec.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        event_similar_rec.adapter = ExploreAdapter()
        event_similar_rec.hasFixedSize()
        event_close.setOnClickListener { onBackPressed() }
    }
}


