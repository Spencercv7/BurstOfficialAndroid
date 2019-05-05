package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout

import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.Notification
import betalab.ca.burstofficialandroid.model.NotificationBroadcast
import betalab.ca.burstofficialandroid.model.NotificationChange
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationAdapter(private var myDataSet: MutableList<Notification>) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val notificationLayout = view.notification_layout as LinearLayout

        //Views for Notification
        val descriptionText = view.notification_card_description as TextView
        val titleText = view.notification_card_event_title as TextView
        val dateText = view.notification_card_date as TextView
        val timeText = view.notification_card_time as TextView
        val locationText = view.notification_card_event_location as TextView
        val notificationRedCircle = view.notification_red_circle as ImageView

        //Views for Broadcast
        val broadcastLayout = view.broadcast_layout as MaterialCardView
        val broadcastText = view.broadcast_text as TextView
        val broadcastImage = view.broadcast_image as ImageView

        //Views for Notification Change
        val originalText = view.notification_change_original as TextView
        val newText = view.notification_change_new as TextView
        val changeImage = view.notification_change_image as ImageView
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false) as View
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element

        if (myDataSet[position] is NotificationBroadcast){    //broadcast notification
            holder.broadcastLayout.visibility = View.VISIBLE
            holder.broadcastImage.visibility = View.VISIBLE
            val broadcastNotification = myDataSet[position] as NotificationBroadcast
            holder.broadcastText.text = broadcastNotification.broadcast
        }
        else if (myDataSet[position] is NotificationChange){ //notification change
            holder.broadcastLayout.visibility = View.VISIBLE
            holder.changeImage.visibility = View.VISIBLE
            val broadcastChange = myDataSet[position] as NotificationChange

            holder.newText.text = broadcastChange.new
            holder.originalText.text = broadcastChange.original
        }

        holder.descriptionText.text = myDataSet[position].description
        holder.titleText.text = myDataSet[position].title
        holder.titleText.text = myDataSet[position].title
        holder.dateText.text = myDataSet[position].date
        holder.timeText.text = myDataSet[position].time
        holder.locationText.text = myDataSet[position].location
        holder.notificationRedCircle.visibility = View.VISIBLE

        //When view clicked, red dot indicating new notification disappears
        holder.notificationLayout.setOnClickListener {
            holder.notificationRedCircle.visibility = View.INVISIBLE
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    override fun getItemCount() = myDataSet.size

}