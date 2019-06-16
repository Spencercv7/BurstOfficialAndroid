package betalab.ca.burstofficialandroid.data.network

import betalab.ca.burstofficialandroid.data.db.entity.EventEntry
import betalab.ca.burstofficialandroid.data.network.response.PeopleResponse
import betalab.ca.burstofficialandroid.data.network.response.ServerReponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface BurstApiService {

    @GET("get_events")
    fun getEventsAsync(): Deferred<List<EventEntry>>

    @GET("get_users")
    fun getClubsAsync(): Deferred<List<PeopleResponse>>

    @POST("register")
    fun registerUserAsync(
        @Query("avatar") avatar: String,
        @Query("interest") interest: Array<String>,
        @Query("name") name: String,
        @Query("program") program: String,
        @Query("school") school: String,
        @Query("token") token: String
    ): Call<ServerReponse>

    @POST("create_event")
    fun createEventAsync(
        @Query("token") token: String,
        @Query("name") eventName: String,
        @Query("location") location: String,
        @Query("description") description: String,
        @Query("start") startTimeEpoch: Long,
        @Query("end") endTimeEpoch: Long,
        @Query("all-day") allDay: Boolean,
        @Query("repeat") repeat: Long,
        @Query("images") images: List<String>? = null,
        @Query("cover-image") coverImage: String? = null,
        @Query("main-image") mainImage: String? = null
    ): Call<ServerReponse>

    @GET("is_email_registered")
    fun isEmailRegistered(@Query("email") email: String): Call<ServerReponse>

    @POST("attend_event")
    fun attendEvent(
        @Query("token") token: String,
        @Query("event-id") eventId: String
    ): Call<ServerReponse>
    @POST("un_attend_event")
    fun unAttendEvent(
        @Query("token") token: String,
        @Query("event-id") eventId: String
    ): Call<ServerReponse>

    @GET("get_featured_events")
    fun getFeaturedEvents(@Query("num") eventsToReturn: Int): Deferred<List<EventEntry>>
    @GET("get_users_for_event")
    fun getUsersForEvent(@Query("event-id") eventId: String): Deferred<List<PeopleResponse>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): BurstApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://burst-internal.herokuapp.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BurstApiService::class.java)
        }
    }
}