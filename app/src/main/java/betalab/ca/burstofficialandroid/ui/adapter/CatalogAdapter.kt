package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.model.CatalogItem
import betalab.ca.burstofficialandroid.R.layout.item_resource_card
import kotlinx.android.synthetic.main.item_resource_card.view.*

class CatalogAdapter(private val dataList: List<CatalogItem>, private val clickListener: (CatalogItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_resource_card, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PartViewHolder).bind(dataList[position], clickListener)
    }

    override fun getItemCount() = dataList.size

    class PartViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: CatalogItem, clickListener: (CatalogItem) -> Unit) {
            itemView.resource_card_title.text = event.title
            itemView.resource_card_desc.text = event.desc
            itemView.setOnClickListener { clickListener(event) }
        }
    }

}