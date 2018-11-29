package com.ottamotta.mozoli.dto

import kotlinx.serialization.Serializable

@Serializable
class Rating {
    var rank: Int? = null
    val userId: String? = null
    val userName: String? = null
    val score: Double? = null
}