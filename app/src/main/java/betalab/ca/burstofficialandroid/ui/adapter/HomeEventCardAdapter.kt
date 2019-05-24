package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.model.EventCardData
import kotlinx.android.synthetic.main.item_home_event.view.*
import betalab.ca.burstofficialandroid.R.layout.item_home_event as item_home_event1

class HomeEventCardAdapter(private val dataList: List<EventCardData>, private val clickListener: (EventCardData) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_home_event1, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PartViewHolder).bind(dataList[position], clickListener)
    }

    override fun getItemCount() = dataList.size


    class PartViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event : EventCardData, clickListener: (EventCardData) -> Unit) {
            itemView.card_label.text = event.name
            itemView.card_description.text = event.description
            itemView.card_date.text = event.date
            itemView.card_location.text = event.location
            itemView.card_time.text = event.time
            itemView.setOnClickListener {clickListener(event)}
        }
    }

}


