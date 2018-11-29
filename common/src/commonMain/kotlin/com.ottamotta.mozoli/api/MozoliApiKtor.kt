package com.ottamotta.mozoli.api

import com.ottamotta.mozoli.config.ServerConfig
import com.ottamotta.mozoli.dto.*
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.features.ExpectSuccess
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.response.readText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parseList

@ImplicitReflectionSerializer
class MozoliApiKtor(
    private val tokenProvider: suspend () -> String?
) : MozoliApi {

    private val serverUrl = ServerConfig.SERVER_URL
    private val client: HttpClient

    private val jsonParser = JSON.nonstrict

    private var kotlinSerializer = KotlinxSerializer(jsonParser)

    private val AUTH_HEADER = HttpHeaders.Authorization
    private val TOKEN_PREFIX = "Bearer "

    init {
        client = HttpClient {
            install(JsonFeature) {
                serializer = kotlinSerializer
            }
            install(ExpectSuccess)
        }
    }

    override suspend fun getUserProfile(): User {
        return client.request {
            url("${serverUrl}/user/")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        }
    }

    override suspend infix fun getEventsByCity(cityId: String): List<Event> =
        parseList(HttpRequestBuilder().apply {
                url("${serverUrl}/event/city/${cityId}")
                method = HttpMethod.Get
                header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
            })

    override suspend fun getRatingByEvent(eventId: String, gender: String?): List<Rating> {
        return parseList(HttpRequestBuilder().apply {
            url("${serverUrl}/rating/event/${eventId}")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        })
    }

    override suspend fun getProblemsForEvent(eventId: String): List<Problem> {
        return parseList(HttpRequestBuilder().apply  {
            url("${serverUrl}/problem/event/$eventId/withoutSolving")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        })
    }

    override suspend fun getProblemsForEventWithSolutions(eventId: String): List<Problem> {
        return parseList(HttpRequestBuilder().apply  {
            url("${serverUrl}/problem/event/${eventId}/withSolvingsForUser")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        })
    }

    override suspend fun solve(solution: Solution): Solution {
        return client.request {
            url("${serverUrl}/solving/")
            method = HttpMethod.Post
            body = kotlinSerializer.write(solution)
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        }
    }

    private inline suspend fun <reified T : Any> parseList(builder: HttpRequestBuilder): List<T> {
        return jsonParser.parseList(client.call(builder).response.readText())
    }

}