package com.ottamotta.mozoli

import kotlin.jvm.JvmField
import kotlin.jvm.JvmName

class Solution(var problemId: String? = null) {
    var isFlash: Boolean = false
        @JvmName("getIsFlash")
        get() = field
    val id: String? = null
    var isRedpoint: Boolean? = null
        @JvmName("getIsRedpoint")
        get() = field
    var attemptsNumber: Int? = null
    val points: Int? = null
    val userId: String? = null
    val solvedDate: String? = null


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