package betalab.ca.burstofficialandroid.ui.util.notification

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import betalab.ca.burstofficialandroid.ui.util.AppConstants

class EventNotificationActionReciever : BroadcastReceiver() {
    companion object {
        val NOTIFICATION = "notification"
    }
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstants.ACTION_SHOW_EVENT_NOTIFICATION -> {
                NotificationUtil.showEventNotification(context, intent.getParcelableExtra(NOTIFICATION) as Notification)
            }
        }
    }
}