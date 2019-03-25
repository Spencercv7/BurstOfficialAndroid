package betalab.ca.burstandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R

class HomeEventCardAdapter(private val textToBind : Array<CardOBJ>) : RecyclerView.Adapter<HomeEventCardAdapter.ViewHolder>() {

    class ViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view){
        val cardLabel : TextView = view.findViewById(R.id.card_label)
        val cardDesc : TextView = view.findViewById(R.id.card_description)
        val cardDate : TextView = view.findViewById(R.id.card_date)
        val cardTime : TextView = view.findViewById(R.id.card_time)
        val cardLoc : TextView = view.findViewById(R.id.card_location)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeEventCardAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_card_event, parent, false) as ViewGroup
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curCard : CardOBJ = textToBind[position]
        holder.cardLabel.text = curCard.label
        holder.cardDesc.text = curCard.description
        holder.cardDate.text = curCard.date
        holder.cardTime.text = curCard.time
        holder.cardLoc.text = curCard.location
    }

    override fun getItemCount() = textToBind.size
}


