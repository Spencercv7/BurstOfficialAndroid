package betalab.ca.burstofficialandroid.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.MapSearch
import kotlinx.android.synthetic.main.item_map_search_card.view.*

class CampusMapAdapter(private var myDataSet: MutableList<MapSearch>) : RecyclerView.Adapter<CampusMapAdapter.MyViewHolder>() {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val titleText = view.map_search_card_title as TextView
        val subtitleText = view.map_search_card_subtitle as TextView
        val statusText = view.map_search_card_status as TextView
        val infoText = view.map_search_card_info as TextView
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_map_search_card, parent, false) as View
            view.setOnClickListener { Toast.makeText(view.context, "Test", Toast.LENGTH_LONG).show() }
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.titleText.text = myDataSet[position].title
        holder.subtitleText.text = myDataSet[position].subtitle
        holder.statusText.text = myDataSet[position].status
        holder.infoText.text = myDataSet[position].info
        if (myDataSet[position].status == "Closed")
            holder.statusText.setTextColor(Color.RED)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataSet.size

}