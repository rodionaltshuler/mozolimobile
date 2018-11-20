package com.ottamotta.mozoli
import android.util.Log
import com.auth0.android.result.Credentials
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.*

class ApiWrapper(serverUrl : String, private val tokenProvider: () -> Deferred<Credentials?>) {

    private val service: MozoliApiService

    private val TAG = ApiWrapper::class.java.simpleName

    init {
        val mapper = ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        val converterFactory : JacksonConverterFactory = JacksonConverterFactory.create(mapper)

        val httpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor((object : Interceptor {
                override fun intercept(chain: Interceptor.Chain?): Response {
                    val request = chain?.request()
                    val newRequest = request!!.newBuilder()
                        .method(request.method(), request.body())

                    val token = runBlocking { tokenProvider().await() }
                    token?.run {
                        Log.d(TAG, "Setting auth header " + this.idToken!!.slice(IntRange(0, 4)))
                        newRequest.addHeader("Authorization", this.idToken!!)
                    }


                    return chain.proceed(newRequest.build())
                }
            })).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .build()
        service = retrofit.create(MozoliApiService::class.java)
    }

    fun getEventsByCity(cityId: String) = service.eventsByCity(cityId)

    fun getRatingByEvent(eventId: String, gender: String? = null) = service.ratingByEvent(eventId, gender)


}

interface MozoliApiService {

    @GET("api/user/")
    fun getUserProfile() : Deferred<User>

    @GET("api/rating/event/{eventId}")
    fun ratingByEvent(@Path("eventId") eventId : String, @Query("gender") gender : String? = null) : Deferred<List<Rating>>

    @GET("api/event/city/{cityId}")
    fun eventsByCity(@Path("cityId") cityId : String) : Deferred<List<Event>>

    @GET("api/event/{eventId}/withSolvingsForUser")
    fun problemsForEventWithSolutions(@Path("eventId") eventId : String) : Deferred<List<Problem>>

    @GET("api//event/{eventId}/withoutSolving")
    fun problemsForEvent(@Path("eventId") eventId : String) : Deferred<List<Problem>>

    @POST("api/solving/")
    fun submitSolution(@Body solution : Solution) : Deferred<Solution>

}