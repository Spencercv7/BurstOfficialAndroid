package betalab.ca.burstofficialandroid

import android.app.Application
import android.content.res.Configuration
import betalab.ca.burstofficialandroid.data.db.EventsDatabase
import betalab.ca.burstofficialandroid.data.network.*
import betalab.ca.burstofficialandroid.data.repository.EventsRepository
import betalab.ca.burstofficialandroid.data.repository.EventsRepositoryImpl
import betalab.ca.burstofficialandroid.ui.viewmodels.EventsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import net.fortuna.ical4j.util.CompatibilityHints
import net.fortuna.ical4j.util.MapTimeZoneCache
import betalab.ca.burstofficialandroid.data.db.PeopleDatabase
import betalab.ca.burstofficialandroid.data.repository.PeopleRepository
import betalab.ca.burstofficialandroid.data.repository.PeopleRepositoryImpl
import betalab.ca.burstofficialandroid.ui.viewmodels.ClubViewModel
import betalab.ca.burstofficialandroid.ui.viewmodels.ClubViewModelFactory
import java.util.*


class MainApplication: Application(), KodeinAware {
    init {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_UNFOLDING, true)
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true)
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_OUTLOOK_COMPATIBILITY, true)
        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache::class.java.name)
    }

    var sDefSystemLanguage: String? = null
    override fun onCreate() {
        super.onCreate()
        sDefSystemLanguage = Locale.getDefault().language
    }
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        bind() from singleton { EventsDatabase(instance()) }
        bind() from singleton { PeopleDatabase(instance()) }
        bind() from singleton { instance<EventsDatabase>().eventsDao() }
        bind() from singleton { instance<PeopleDatabase>().peopleDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { BurstApiService(instance()) }
        bind<EventsDataSource>() with singleton { EventsDataSourceImpl(instance()) }
        bind<EventsRepository>() with singleton { EventsRepositoryImpl(instance(), instance()) }
        bind<PeopleDataSource>() with singleton { PeopleDataSourceImpl(instance()) }
        bind<PeopleRepository>() with singleton { PeopleRepositoryImpl(instance(), instance()) }
        bind() from provider { EventsViewModelFactory(instance()) }
        bind() from provider { ClubViewModelFactory(instance()) }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        @Suppress("DEPRECATION")
        sDefSystemLanguage = if (android.os.Build.VERSION.SDK_INT>=24){
            newConfig.locales.get(0).language
        } else
            newConfig.locale.language
    }


}