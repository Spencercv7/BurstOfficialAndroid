package betalab.ca.burstofficialandroid.ui.util

import android.content.Context
import android.content.Intent
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.db.entity.PeopleEntry
import betalab.ca.burstofficialandroid.data.network.response.PeopleResponse
import betalab.ca.burstofficialandroid.ui.activity.EventActivity
import betalab.ca.burstofficialandroid.ui.activity.ProfileActivity

class NavigationUtils {
    companion object {

        fun sendToEvent(context: Context, eventID: EventEntry? = null){
            val intent = Intent(context, EventActivity::class.java)
            intent.putExtra("event", eventID!!.eventId)
            context.startActivity(intent)
        }
        fun sendToProfile(context: Context, peopleEntry: PeopleEntry) {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("person", peopleEntry)
            context.startActivity(intent)
        }
    }
}