package betalab.ca.burstofficialandroid.ui.util.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import betalab.ca.burstofficialandroid.ui.util.AppConstants
import betalab.ca.burstofficialandroid.ui.util.NotificationUtil
import java.util.*

class EventNotificationActionReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
//            AppConstants.ACTION_STOP -> {
//                TimerActivity.removeAlarm(context)
//                PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
//                NotificationUtil.hideTimerNotification(context)
//            }
//            AppConstants.ACTION_PAUSE -> {
//                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
//                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
//                val nowSeconds = TimerActivity.nowSeconds
//
//                secondsRemaining -= nowSeconds - alarmSetTime
//                PrefUtil.setSecondsRemaining(secondsRemaining, context)
//
//                TimerActivity.removeAlarm(context)
//                PrefUtil.setTimerState(TimerActivity.TimerState.Paused, context)
//                NotificationUtil.showTimerPaused(context)
//            }
//            AppConstants.ACTION_RESUME -> {
//                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
//                val wakeUpTime = TimerActivity.setAlarm(context, TimerActivity.nowSeconds, secondsRemaining)
//                PrefUtil.setTimerState(TimerActivity.TimerState.Running, context)
//                NotificationUtil.showTimerRunning(context, wakeUpTime)
//            }
            AppConstants.ACTION_START -> {
                NotificationUtil.showEventReminder(context, intent.getSerializableExtra("eventDate") as Calendar)
            }
        }
    }
}