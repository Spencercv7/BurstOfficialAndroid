package betalab.ca.burstandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R

class HomeScheduleAdapter(private val textToBind : Array<ScheduleCardOBJ>) : RecyclerView.Adapter<HomeScheduleAdapter.ViewHolder>() {

    class ViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
        val scheduleLabel : TextView = view.findViewById(R.id.schedule_label)
        val scheduleDescription : TextView = view.findViewById(R.id.schedule_description)
        val scheduleTime : TextView = view.findViewById(R.id.schedule_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScheduleAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_card_schedual, parent, false) as ViewGroup
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curCard : ScheduleCardOBJ = textToBind[position]
        holder.scheduleLabel.text = curCard.label
        holder.scheduleDescription.text = curCard.description
        holder.scheduleTime.text = curCard.time

    }

    override fun getItemCount() = textToBind.size
}