package betalab.ca.burstofficialandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.Notification
import betalab.ca.burstofficialandroid.model.NotificationBroadcast
import betalab.ca.burstofficialandroid.model.NotificationChange

import betalab.ca.burstofficialandroid.ui.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationFragment : Fragment() {

    private lateinit var myDataSet: MutableList<Notification>

    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setData()   //set data to be passed to recycler view adapter
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_notifications,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifications_fragment_recycler_view.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        notifications_fragment_recycler_view.adapter = NotificationAdapter(myDataSet)
        notifications_fragment_recycler_view.hasFixedSize()
    }

    // Function in which we draw data from the database
    // Currently just makes placeholder information
    private fun setData() {
        val notification = Notification(
            "Betalabs is hosting a new event", "Web Tutorial Series Part 2 - Implementation of JavaScript",
            "March 2, 2018", "1PM - 3PM", "Ellis Hall 324"
        )
        val notificationBroadcast = NotificationBroadcast(
            "Betalabs is hosting a new event", "Web Tutorial Series Part 2 - Implementation of JavaScript",
            "March 2, 2018", "1PM - 3PM", "Ellis Hall 324", "This is a broadcast message. This is a broadcast message. This is a broadcast message."
        )
        val notificationChange = NotificationChange(
            "Betalabs is hosting a new event", "Web Tutorial Series Part 2 - Implementation of JavaScript",
            "March 2, 2018", "1PM - 3PM", "Ellis Hall 324", "Ellis Hall 326", "Ellis Hall 324"
        )

        myDataSet = mutableListOf(notification, notificationBroadcast, notificationChange)
    }
}