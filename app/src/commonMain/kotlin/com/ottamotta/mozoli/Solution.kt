package com.ottamotta.mozoli

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

}