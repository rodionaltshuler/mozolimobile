package com.ottamotta.mozoli.dto

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class Gym {
    val id: String? = null
    val name: String? = null
    @Optional
    val locLatitude: Double? = null
    @Optional
    val locLongitude: Double? = null
    @Optional
    val isBoulder: Boolean? = null
    @Optional
    val isLead: Boolean? = null
    @Optional
    val tel: String? = null
    @Optional
    val address: String? = null
    @Optional
    val logoUrl: String? = null
    @Optional
    val logoId: String? = null
    @Optional
    val cityId: String? = null
    @Optional
    val countryId: String? = null
    @Optional
    val cityName: String? = null
    @Optional
    val isCurrentUserAddToFavorite: Boolean = false
    @Optional
    val description: String? = null
    @Optional
    val workingHours: String? = null
    @Optional
    val webSite: String? = null
    @Optional
    val services: String? = null
    @Optional
    val coverUrl: String? = null
    @Optional
    val coverId: String? = null
    @Optional
    val isAllowCustomProblems: Boolean? = null
}
