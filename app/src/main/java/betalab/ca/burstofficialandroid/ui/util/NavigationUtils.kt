package betalab.ca.burstofficialandroid.ui.util

import android.content.Context
import android.content.Intent
import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.ui.activity.EventActivity

class NavigationUtils {
    companion object {

        fun sendToEvent(context: Context, eventID: EventEntry? = null){
            val intent = Intent(context, EventActivity::class.java)
            intent.putExtra("event", eventID!!.eventId)
            context.startActivity(intent)
        }
        fun sendToProfile() {

        }
    }
}