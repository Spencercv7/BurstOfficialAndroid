package betalab.ca.burstofficialandroid.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.model.ExploreHorizontalCardData
import kotlinx.android.synthetic.main.item_explore_horizontal.view.*

class OrganizationCardAdapter(private val clickListener: (EventEntry) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList : ArrayList<EventEntry> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_explore_horizontal, parent, false) as View
        return ExploreViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ExploreViewHolder).bind(dataList[position], clickListener)
    }
    fun updateData(events: List<EventEntry>) {
        this.dataList.clear()
        this.dataList.addAll(events)
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size

    class ExploreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(event : EventEntry, clickListener: (EventEntry) -> Unit) {
            itemView.test_text.text = event.name
            itemView.setOnClickListener {clickListener(event)}
        }
    }
}