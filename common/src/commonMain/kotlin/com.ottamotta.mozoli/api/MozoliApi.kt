package com.ottamotta.mozoli.api

import com.ottamotta.mozoli.dto.*

interface MozoliApi {
    suspend fun getUserProfile() : User
    suspend infix fun getEventsByCity(cityId: String): List<Event>
    suspend fun getRatingByEvent(eventId: String, gender: String? = null): List<Rating>
    suspend fun getProblemsForEvent(eventId: String): List<Problem>
    suspend fun getProblemsForEventWithSolutions(eventId: String): List<Problem>
    suspend infix fun solve(solution: Solution): Solution
}