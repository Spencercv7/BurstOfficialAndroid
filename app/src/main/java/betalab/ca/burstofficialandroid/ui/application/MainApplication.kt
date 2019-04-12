package betalab.ca.burstofficialandroid.ui.application

import android.app.Application
import android.content.res.Resources
import android.os.StrictMode.VmPolicy
import android.os.StrictMode
import betalab.ca.burstofficialandroid.BuildConfig


@Suppress("unused")
class MainApplication: Application() {
    companion object {
        lateinit var resources: Resources
    }
    override fun onCreate() {
        MainApplication.resources = resources
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
        super.onCreate()
    }

}