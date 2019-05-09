package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.item_explore_horizontal.view.*
import kotlinx.android.synthetic.main.item_resource_card.view.*

class ResourceAdapter : RecyclerView.Adapter<ResourceAdapter.MyViewHolder>() {


    private val myDataset = listOf("QWEB", "QWEB", "QWEB", "QWEB")

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val resourceTitle = view.resource_card_title as TextView
        //val resourceDesc = view.resource_card_desc as TextView
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_resource_card, parent, false) as View
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.resourceTitle.text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}