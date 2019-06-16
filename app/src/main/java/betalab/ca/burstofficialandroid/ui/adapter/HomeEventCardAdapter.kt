package betalab.ca.burstofficialandroid.ui.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.model.EventCardData
import kotlinx.android.synthetic.main.item_home_event.view.*
import org.threeten.bp.Instant
import java.util.*
import betalab.ca.burstofficialandroid.R.layout.item_home_event as item_home_event1

class HomeEventCardAdapter(private val clickListener: (EventEntry) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList : ArrayList<EventEntry> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_home_event1, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PartViewHolder).bind(dataList[position], clickListener)
    }

    fun updateData(events: List<EventEntry>) {
        this.dataList.clear()
        this.dataList.addAll(events)
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size


    class PartViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event : EventEntry, clickListener: (EventEntry) -> Unit) {
            itemView.card_label.text = event.name
            itemView.card_description.text = event.host
            itemView.card_date.text = DateFormat.format("h:mm a MMM dd", Date(event.start*1000))
            itemView.card_location.text = event.location
            itemView.card_time.text = DateFormat.format("h:mm a MMM dd", Date(event.end*1000))
            itemView.setOnClickListener {clickListener(event)}
        }
    }

}


