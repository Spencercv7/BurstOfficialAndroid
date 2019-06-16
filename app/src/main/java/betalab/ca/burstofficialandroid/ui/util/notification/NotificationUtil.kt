package betalab.ca.burstofficialandroid.ui.util.notification

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.activity.MainActivity

import androidx.core.app.NotificationCompat
import betalab.ca.burstofficialandroid.ui.util.AppConstants.Companion.ACTION_SHOW_EVENT_NOTIFICATION
import java.util.*


class NotificationUtil {
    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Event Reminders"
        private const val TIMER_ID = 0

        fun showEventNotification(context: Context, notification: Notification){
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(
                CHANNEL_ID_TIMER,
                CHANNEL_NAME_TIMER, false)
            nManager.notify(TIMER_ID, notification)
        }

        fun scheduleEventNotification(context: Context, future: Calendar) {
            val pendingIntent = Intent(context, EventNotificationActionReciever::class.java).let { intent ->
                intent.action = ACTION_SHOW_EVENT_NOTIFICATION
                intent.putExtra(
                    EventNotificationActionReciever.NOTIFICATION,
                    getBasicNotificationBuilder(
                        context,
                        CHANNEL_ID_TIMER,
                        false
                    ).setContentTitle("Event Title")
                        .setContentText("Event in one hour")
                        .setContentIntent(
                            getPendingIntentWithStack(
                                context,
                                MainActivity::class.java
                            )
                        ).build()
                )

                PendingIntent.getBroadcast(context, 0, intent, 0)
            }

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, future.timeInMillis, pendingIntent)
        }

        fun hideTimerNotification(context: Context){
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }

        private fun getBasicNotificationBuilder(context: Context, channelId: String, playSound: Boolean)
                : NotificationCompat.Builder{
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_icon_location_on)
                .setAutoCancel(true)
                .setDefaults(0)
            if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent{
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel(channelID: String,
                                                                  channelName: String,
                                                                  playSound: Boolean){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW
                val nChannel = NotificationChannel(channelID, channelName, channelImportance)
                nChannel.enableLights(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }
    }
}