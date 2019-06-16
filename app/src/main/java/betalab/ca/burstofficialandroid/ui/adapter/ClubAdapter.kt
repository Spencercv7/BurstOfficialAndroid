package betalab.ca.burstofficialandroid.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry
import betalab.ca.burstofficialandroid.model.ExploreHorizontalCardData
import kotlinx.android.synthetic.main.item_explore_horizontal.view.*

class ClubAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList : ArrayList<PeopleEntry> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_explore_horizontal, parent, false) as View
        return ClubViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ClubViewHolder).bind(dataList[position])
    }
    fun updateData(events: List<PeopleEntry>) {
        this.dataList.clear()
        this.dataList.addAll(events)
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size

    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(event : PeopleEntry) {
            itemView.test_text.text = event.name
        }
    }
}