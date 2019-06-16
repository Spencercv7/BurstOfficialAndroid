package betalab.ca.burstofficialandroid.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.data.EventsDao
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.network.BurstApiService
import betalab.ca.burstofficialandroid.data.network.response.ServerReponse
import betalab.ca.burstofficialandroid.data.repository.EventsRepository
import betalab.ca.burstofficialandroid.internal.glide.GlideApp
import betalab.ca.burstofficialandroid.ui.adapter.ImageCardAdapter
import betalab.ca.burstofficialandroid.ui.adapter.SimilarCardAdapter
import betalab.ca.burstofficialandroid.ui.util.ConversionUtils
import betalab.ca.burstofficialandroid.ui.view.CircularImageView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_event_page.*
import kotlinx.android.synthetic.main.item_explore_main_event_placeholder.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EventActivity : ScopedActivity(), KodeinAware {

    private lateinit var galleryRec: RecyclerView
    private lateinit var similarRec: RecyclerView
    override val kodein by closestKodein()
    private val eventRepository: EventsRepository by instance()
    private val burstApiService: BurstApiService by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_page)
        launch {
            val users = burstApiService.getUsersForEvent(intent.getStringExtra("event")).await()
            with(users) {
                for (i in 0 until (if (size < 10) size else 10)) {
                    val imageViewToAdd = CircularImageView(this@EventActivity)
                    val layoutParams = LinearLayout.LayoutParams(
                        ConversionUtils.dpToPixels(this@EventActivity, 48),
                        ConversionUtils.dpToPixels(this@EventActivity, 48)
                    )
                    layoutParams.gravity = Gravity.CENTER_VERTICAL
                    imageViewToAdd.layoutParams = layoutParams
                    if (get(i).avatar.isEmpty())
                        imageViewToAdd.setImageResource(R.drawable.icon_profile_filled)
                    else
                        GlideApp.with(this@EventActivity).load(get(i).avatar).centerCrop().into(imageViewToAdd)
                    imageViewToAdd.layout(ConversionUtils.dpToPixels(this@EventActivity, -4), 0, 0, 0)
                    attending_event_container.addView(imageViewToAdd)
                }
                if (size >= 10)
                    with(ImageView(this@EventActivity)) {
                        layoutParams.width = ConversionUtils.dpToPixels(this@EventActivity, 24)
                        layoutParams.height = ConversionUtils.dpToPixels(this@EventActivity, 24)
                        setImageResource(R.drawable.ic_chevron_right_black_24dp)
                        setColorFilter(ContextCompat.getColor(this@EventActivity, R.color.black_50))
                        adjustViewBounds = true
                        attending_event_container.addView(this)
                    }
            }
        }
        bindUI()

    }

    private fun bindUI() = launch(Dispatchers.IO) {
        val eventID = intent.getStringExtra("event")
        // coroutine on IO
        val result = eventRepository.getEventById(eventID)

        Log.e("test", result.toString())
        host_name.text = result.hostName
        event_name_textView.text = result.name
        val start = ZonedDateTime.ofInstant(Instant.ofEpochSecond(result.start), ZoneId.systemDefault())
        val end = ZonedDateTime.ofInstant(Instant.ofEpochSecond(result.end), ZoneId.systemDefault())
        val timeText = if (start.dayOfYear == end.dayOfYear && start.year == end.year) {
            DateFormat.format(
                "MMM dd, yyyy h:mm a - ",
                Date(result.start * 1000)
            ).toString() + DateFormat.format(
                "h:mm a",
                Date(result.end * 1000)
            )
        } else {
            DateFormat.format(
                "MMM dd, yyyy h:mm a - ",
                Date(result.start * 1000)
            ).toString() + DateFormat.format(
                "MMM dd ,yyyy h:mm a",
                Date(result.end * 1000)
            )
        }
        event_data_time.text = timeText
        event_location.text = result.location
        if (result.description.isNullOrBlank())
            event_body.visibility = View.GONE
        else
            event_body.text = result.description
        if (result.attendees.contains(FirebaseAuth.getInstance().currentUser!!.email))
            event_register_button.text = getString(R.string.attending_event_string)
        attending_event_count.text = result.attendees.size.toString()
        if (result.coverImage != null)
            runOnUiThread {
                GlideApp.with(this@EventActivity).load(result.coverImage).into(top_bar_image)
            }
        if(result.mainImage != null){
            runOnUiThread {
                event_photo.visibility = ImageView.VISIBLE
                main_image_container.visibility = ImageView.VISIBLE
                val circularProgressDrawable = CircularProgressDrawable(this@EventActivity)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                GlideApp.with(this@EventActivity).load(result.mainImage).placeholder(circularProgressDrawable).into(event_photo)
            }
        }
        // This is the array that will have to be added too for the gallery section of the events page. Unsure of image type when being read from server.
        val imageArray = arrayOf(R.drawable.card_temp_backing, R.drawable.card_temp_backing)

        // This will need its own image and text source to pull from......
        val textArray = arrayOf(arrayOf("Sample Title", "Sample Author"), arrayOf("SampleTitle", "Sample Author"))

        galleryRec = findViewById(R.id.event_gallery_rec)
        galleryRec.layoutManager = LinearLayoutManager(this@EventActivity, LinearLayoutManager.HORIZONTAL, false)
        galleryRec.adapter = ImageCardAdapter(GlideApp.with(this@EventActivity))

        similarRec = findViewById(R.id.event_similar_rec)
        similarRec.layoutManager = LinearLayoutManager(this@EventActivity, LinearLayoutManager.HORIZONTAL, false)
        similarRec.adapter = SimilarCardAdapter(imageArray, textArray)

        event_close.setOnClickListener { onBackPressed() }
        setRegisterButtonListener(eventID, result.attendees)
        if(!result.images.isNullOrEmpty()) {
            gallery_text.visibility = TextView.VISIBLE
            event_gallery_rec.visibility = RecyclerView.VISIBLE
            (event_gallery_rec.adapter as ImageCardAdapter).setData(result.images)
        }
    }
    private fun setRegisterButtonListener(eventID: String, attendees: Array<String>) {
        event_register_button.setOnClickListener {
            event_register_button.isClickable = false
            FirebaseAuth.getInstance().currentUser!!.getIdToken(true).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.token?.let {
                        Log.e("token:", it)
                        if (attendees.contains(FirebaseAuth.getInstance().currentUser!!.email)) {
                            burstApiService.unAttendEvent(it, eventID)
                                .enqueue(object : Callback<ServerReponse> {
                                    override fun onResponse(
                                        call: Call<ServerReponse>,
                                        response: Response<ServerReponse>
                                    ) {
                                        event_register_button.text = getString(R.string.register_event_string)
                                        event_register_button.isClickable = true
                                    }

                                    override fun onFailure(call: Call<ServerReponse>, t: Throwable) {
                                        Toast.makeText(this@EventActivity, "Server error", Toast.LENGTH_SHORT)
                                            .show()
                                        event_register_button.isClickable = true
                                    }

                                })
                        } else {
                            burstApiService.attendEvent(it, eventID).enqueue(object : Callback<ServerReponse> {
                                override fun onResponse(
                                    call: Call<ServerReponse>,
                                    response: Response<ServerReponse>
                                ) {
                                    event_register_button.text = getString(R.string.attending_event_string)
                                    event_register_button.isClickable = true
                                }

                                override fun onFailure(call: Call<ServerReponse>, t: Throwable) {
                                    Toast.makeText(this@EventActivity, "Server error", Toast.LENGTH_SHORT)
                                        .show()
                                    event_register_button.isClickable = true
                                }

                            })
                        }

                    }
                } else {
                    event_register_button.isClickable = true
                }
            }

        }
    }
}