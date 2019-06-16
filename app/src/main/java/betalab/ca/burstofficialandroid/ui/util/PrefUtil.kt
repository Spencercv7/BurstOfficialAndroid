package betalab.ca.burstofficialandroid.ui.util

import android.content.Context
import android.preference.PreferenceManager
import com.google.firebase.auth.GetTokenResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class PrefUtil {
    companion object {

        private const val TIMER_LENGTH_ID = "com.resocoder.timer.timer_length"
        private const val CAL_URL_ID = "com.betalab.calendarurl"
        private const val COMPLETED_ONBOARDING = "com.betalab.completedOnBoarding"

        fun completedOnboarding(context: Context): Boolean {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(COMPLETED_ONBOARDING, false)
        }
        fun setCompletedOnboarding(completed:Boolean, context:Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putBoolean(COMPLETED_ONBOARDING, completed)
            editor.apply()
        }

        private const val PROFILE_PIC_URL_ID = "com.betalab.profilepicurl"

        fun getProfilePicUrl(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(PROFILE_PIC_URL_ID, null)
        }

        fun setProfilePicUrl(picUrl: String, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(PROFILE_PIC_URL_ID, picUrl)
            editor.apply()
        }

        fun getCalUrl(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(CAL_URL_ID, null)
        }
        fun setCalUrl(calUrl: String, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(CAL_URL_ID, calUrl)
            editor.apply()
        }

        fun getTimerLength(context: Context): Int{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt(TIMER_LENGTH_ID, 10)
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.resocoder.timer.previous_timer_length_seconds"

        fun getPreviousTimerLengthSeconds(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }


        private const val EVENT_STATE_ID = "com.betalab.event.event_state"



        private const val ALARM_SET_TIME_ID = "com.resocoder.timer.backgrounded_time"

        fun getAlarmSetTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(ALARM_SET_TIME_ID, 0)
        }

        fun setAlarmSetTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }
    }
}