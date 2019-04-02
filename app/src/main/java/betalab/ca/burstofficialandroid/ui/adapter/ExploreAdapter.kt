package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.item_explore_horizontal.view.*

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.MyViewHolder>() {


    private val myDataset = listOf("QHacks", "Anime Club", "EngSoc")

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView = view.test_text as TextView
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_explore_horizontal, parent, false) as View
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size


    // Function in which we draw data from the database
    // Currently just makes placeholder information (should this be here or in fragment?)
/*    fun setData(){

    }*/

}