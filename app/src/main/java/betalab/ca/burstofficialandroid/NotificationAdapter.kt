package betalab.ca.burstofficialandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.model.Notification
import kotlinx.android.synthetic.main.notifications_recycler_card.view.*

class NotificationAdapter(private var myDataSet: MutableList<Notification>) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val descriptionText = view.notification_card_description as TextView
        val titleText = view.notification_card_event_title as TextView
        val dateText = view.notification_card_date as TextView
        val timeText = view.notification_card_time as TextView
        val locationText = view.notification_card_event_location as TextView
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notifications_recycler_card, parent, false) as View
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element
        holder.descriptionText.text = myDataSet[position].description
        holder.titleText.text = myDataSet[position].title
        holder.titleText.text = myDataSet[position].title
        holder.dateText.text = myDataSet[position].date
        holder.timeText.text = myDataSet[position].time
        holder.locationText.text = myDataSet[position].location
    }

    // Return the size of your data set (invoked by the layout manager)
    override fun getItemCount() = myDataSet.size




}