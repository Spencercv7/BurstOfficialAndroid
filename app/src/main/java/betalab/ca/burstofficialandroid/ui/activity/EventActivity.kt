package betalab.ca.burstofficialandroid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.adapter.ImageCardAdapter
import betalab.ca.burstofficialandroid.ui.adapter.SimilarCardAdapter
import kotlinx.android.synthetic.main.activity_event_page.*

class EventActivity : AppCompatActivity() {

    private lateinit var galleryRec: RecyclerView
    private lateinit var similarRec : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_page)

        // This is the array that will have to be added too for the gallery section of the events page. Unsure of image type when being read from server.
        val imageArray = arrayOf(R.drawable.card_temp_backing, R.drawable.card_temp_backing)

        // This will need its own image and text source to pull from......
        val textArray = arrayOf(arrayOf("Sample Title", "Sample Author"), arrayOf("SampleTitle", "Sample Author"))

        galleryRec = findViewById(R.id.event_gallery_rec)
        galleryRec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        galleryRec.adapter = ImageCardAdapter(imageArray)

        similarRec = findViewById(R.id.event_similar_rec)
        similarRec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarRec.adapter = SimilarCardAdapter(imageArray,textArray)

        event_close.setOnClickListener { onBackPressed() }

    }

}