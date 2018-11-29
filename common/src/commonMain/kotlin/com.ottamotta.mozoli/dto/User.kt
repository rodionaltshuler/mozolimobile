package com.ottamotta.mozoli.dto

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class User {
    val id: String? = null
    @Optional
    val firstName: String? = null
    @Optional
    val lastName: String? = null
    @Optional
    val gender: String? = null
    @Optional
    val userName: String? = null
    @Optional
    val email: String? = null
    @Optional
    val gradeSystem: String? = null
    @Optional
    val avaUrl: String? = null
    @Optional
    val externalId: String? = null
    @Optional
    val countryId: String? = null
    @Optional
    val cityId: String? = null
    @Optional
    val language: String? = null
}