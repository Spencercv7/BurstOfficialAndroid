package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.ExploreHorizontalCardData
import kotlinx.android.synthetic.main.item_explore_horizontal.view.*

class ExploreAdapter(private val dataList : List<ExploreHorizontalCardData>, private val clickListener: (ExploreHorizontalCardData) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_explore_horizontal, parent, false) as View
        return ExploreViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ExploreViewHolder).bind(dataList[position], clickListener)
    }

    override fun getItemCount() = dataList.size

    class ExploreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(event : ExploreHorizontalCardData, clickListener: (ExploreHorizontalCardData) -> Unit) {
            itemView.test_text.text = event.text
            itemView.setOnClickListener {clickListener(event)}
        }
    }
}