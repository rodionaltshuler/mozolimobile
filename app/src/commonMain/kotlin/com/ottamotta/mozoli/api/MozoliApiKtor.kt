package com.ottamotta.mozoli.api

import com.ottamotta.mozoli.*
import io.ktor.client.HttpClient
import io.ktor.client.features.feature
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.json.defaultSerializer
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod


class MozoliApiKtor(
    private val serverUrl: String,
    private var jsonSerializer: JsonSerializer? = null,
    private val tokenProvider: suspend () -> String?
) : MozoliApi {

    private val client: HttpClient

    private val AUTH_HEADER = "Authorization";
    private val TOKEN_PREFIX = "Bearer "

    init {
        client = HttpClient {
            install(JsonFeature) {
                serializer = jsonSerializer ?: defaultSerializer()
            }
        }
        jsonSerializer = client.feature(JsonFeature)?.serializer
    }

    override suspend fun getUserProfile(): User {
        return client.request {
            url("${serverUrl}/user/")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        }
    }

    override suspend infix fun getEventsByCity(cityId: String): List<Event> {
        return client.request {
                url("${serverUrl}/event/city/${cityId}")
                method = HttpMethod.Get
                header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
            }

    }

    override suspend fun getRatingByEvent(eventId: String, gender: String?): List<Rating> {
        return client.request {
            url("${serverUrl}/rating/event/${eventId}")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        }
    }

    override suspend fun getProblemsForEvent(eventId: String): List<Problem> {
        return client.request {
            url("${serverUrl}/problem/event/$eventId/withoutSolving")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        }
    }

    override suspend fun getProblemsForEventWithSolutions(eventId: String): List<Problem> {
        return client.request {
            url("${serverUrl}/problem/event/${eventId}/withSolvingsForUser")
            method = HttpMethod.Get
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        }
    }

    override suspend fun solve(solution: Solution): Solution {
        return client.request {
            url("${serverUrl}/solving/")
            method = HttpMethod.Post
            body = jsonSerializer!!.write(solution)
            header(AUTH_HEADER, TOKEN_PREFIX + tokenProvider())
        }
    }
}