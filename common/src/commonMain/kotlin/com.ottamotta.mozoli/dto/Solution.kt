package com.ottamotta.mozoli.dto

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName

@Serializable
class Solution(var problemId: String? = null) {
    @Optional
    var isFlash: Boolean? = false
        @JvmName("getIsFlash")
        get() = field
    @Optional
    val id: String? = null
    @Optional
    var isRedpoint: Boolean? = false
        @JvmName("getIsRedpoint")
        get() = field
    @Optional
    var attemptsNumber: Int? = null
    @Optional
    val points: Int? = null
    @Optional
    val userId: String? = null
    @Optional
    val solvedDate: String? = null


    fun solved() = isFlash?:false || isRedpoint?:false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Solution

        if (id != other.id) return false
        if (attemptsNumber != other.attemptsNumber) return false
        if (isFlash != other.isFlash) return false
        if (isRedpoint != other.isRedpoint) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (attemptsNumber ?: 0)
        result = 31 * result + isFlash.hashCode()
        result = 31 * result + isRedpoint.hashCode()
        return result
    }


}