package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.Card
import betalab.ca.burstofficialandroid.R.layout.item_home_event as item_home_event1

class HomeEventCardAdapter(private val textToBind : Array<Card>) : RecyclerView.Adapter<HomeEventCardAdapter.ViewHolder>() {

    class ViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view){
        val cardLabel : TextView = view.findViewById(R.id.card_label)
        val cardDesc : TextView = view.findViewById(R.id.card_description)
        val cardDate : TextView = view.findViewById(R.id.card_date)
        val cardTime : TextView = view.findViewById(R.id.card_time)
        val cardLoc : TextView = view.findViewById(R.id.card_location)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(item_home_event1, parent, false) as ViewGroup
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curCard : Card = textToBind[position]
        holder.cardLabel.text = curCard.label
        holder.cardDesc.text = curCard.description
        holder.cardDate.text = curCard.date
        holder.cardTime.text = curCard.time
        holder.cardLoc.text = curCard.location
    }

    override fun getItemCount() = textToBind.size
}


