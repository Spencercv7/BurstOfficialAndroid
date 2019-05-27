package betalab.ca.burstofficialandroid

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.StrictMode.VmPolicy
import android.os.StrictMode
import betalab.ca.burstofficialandroid.data.db.EventsDatabase
import betalab.ca.burstofficialandroid.data.network.*
import betalab.ca.burstofficialandroid.data.repository.EventsRepository
import betalab.ca.burstofficialandroid.data.repository.EventsRepositoryImpl
import betalab.ca.burstofficialandroid.ui.events.EventsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*


class MainApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        bind() from singleton { EventsDatabase(instance()) }
        bind() from singleton { instance<EventsDatabase>().eventsDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { BurstApiService(instance()) }
        bind<EventsDataSource>() with singleton { EventsDataSourceImpl(instance()) }
        bind<EventsRepository>() with singleton { EventsRepositoryImpl(instance(), instance()) }
        bind() from provider { EventsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }

}