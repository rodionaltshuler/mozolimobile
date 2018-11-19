package com.ottamotta.mozoli
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ApiWrapper(serverUrl : String) {

    private val service: MozoliApiService

    init {
        val mapper = ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        val converterFactory : JacksonConverterFactory = JacksonConverterFactory.create(mapper)

        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
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

}