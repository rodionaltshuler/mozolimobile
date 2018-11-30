package com.ottamotta.mozoli.api

import com.ottamotta.mozoli.dto.*

interface MozoliApi {
    suspend fun getUserProfile() : User
    suspend infix fun getEventsByCity(cityId: String): List<Event>
    suspend fun getRatingByEvent(eventId: String, gender: String? = null): List<Rating>
    suspend fun getProblemsForEvent(eventId: String): List<Problem>
    suspend fun getProblemsForEventWithSolutions(eventId: String): List<Problem>
    suspend infix fun solve(solution: Solution): Solution

    fun getUserProfile(onSuccess: (User) -> Unit = {}, onError: (Throwable) -> Unit = {})
    fun getEventsByCity(cityId: String, onSuccess: (List<Event>) -> Unit = {}, onError: (Throwable) -> Unit = {})
    fun getRatingByEvent(eventId: String, gender: String? = null, onSuccess: (List<Rating>) -> Unit = {}, onError: (Throwable) -> Unit = {})
    fun getProblemsForEvent(eventId: String, onSuccess: (List<Problem>) -> Unit = {}, onError: (Throwable) -> Unit = {})
    fun getProblemsForEventWithSolutions(eventId: String, onSuccess: (List<Problem>) -> Unit = {}, onError: (Throwable) -> Unit = {})
    fun solve(solution: Solution, onSuccess: (Solution) -> Unit, onError: (Throwable) -> Unit = {})

}