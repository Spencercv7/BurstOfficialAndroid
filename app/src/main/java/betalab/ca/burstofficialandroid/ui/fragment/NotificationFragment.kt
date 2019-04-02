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
import betalab.ca.burstofficialandroid.ui.adapter.NotificationAdapter

class NotificationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
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
        recyclerView = view.findViewById(R.id.notifications_fragment_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = NotificationAdapter(myDataSet)
        recyclerView.hasFixedSize()
    }

    // Function in which we draw data from the database
    // Currently just makes placeholder information
    private fun setData() {
        val notification1 = Notification(
            "Betalabs is hosting a new event", "Web Tutorial Series Part 2 - Implementation of JavaScript",
            "March 2, 2018", "1PM - 3PM", "Ellis Hall 324"
        )
        val notification2 = Notification(
            "Betalabs is hosting a new event", "Meet and Greet Tech Leaders - Elon Musk",
            "February 21, 2018", "1AM - 3AM", "Jeffery Hall 101"
        )
        val notification3 = Notification(
            "Betalabs is hosting a new event", "Web Tutorial Series Part 1 - Implementation of JavaScript",
            "January 31, 2018", "1PM - 3PM", "Ellis Hall 324"
        )
        val notification4 = Notification(
            "Betalabs is hosting a new event", "Intro to Android - Creating your First App",
            "April 15, 2018", "10PM - 11PM", "Dunning Hall 324"
        )
        myDataSet = mutableListOf(notification1, notification2, notification3, notification4)
    }






}