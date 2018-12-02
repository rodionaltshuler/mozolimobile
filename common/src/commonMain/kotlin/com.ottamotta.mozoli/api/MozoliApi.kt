package com.ottamotta.mozoli.api

import com.ottamotta.mozoli.dto.*

interface MozoliApi {

    suspend fun getUserProfileAsync() : User
    suspend infix fun getEventsByCityAsync(cityId: String): List<Event>
    suspend fun getRatingByEventAsync(eventId: String, gender: String? = null): List<Rating>
    suspend fun getProblemsForEventAsync(eventId: String): List<Problem>
    suspend fun getProblemsForEventWithSolutionsAsync(eventId: String): List<Problem>
    suspend infix fun solveAsync(solution: Solution): Solution

    //native (iOS) doesn't understand suspend functions, so here are regular blocking functions equivalent
    fun getUserProfile() = runBlocking { getUserProfileAsync() }
    fun getEventsByCity(cityId: String) = runBlocking { getEventsByCityAsync(cityId) }
    fun getRatingByEvent(eventId: String, gender: String?) = runBlocking { getRatingByEventAsync(eventId, gender) }
    fun getProblemsForEvent(eventId: String) = runBlocking { getProblemsForEventAsync(eventId) }
    fun getProblemsForEventWithSolutions(eventId: String) = runBlocking { getProblemsForEventWithSolutionsAsync(eventId) }
    fun solve(solution: Solution) : Solution = runBlocking { solveAsync(solution) }

}