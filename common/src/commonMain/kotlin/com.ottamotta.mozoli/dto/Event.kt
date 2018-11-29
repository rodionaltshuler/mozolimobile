package com.ottamotta.mozoli.dto

import kotlinx.serialization.Serializable

@Serializable
class Event {
    val id: String? = null
    val name: String? = null
    val startDate: String? = null
    val endDate: String? = null
    val isPrivate: Boolean? = null
    val gym: Gym? = null
    val price: String? = null
    val currency: String? = null
    val logoUrl: String? = null
    val coverUrl: String? = null
    val facebookEventUrl: String? = null
    val description: String? = null
    val isBoulder: Boolean? = null
    val isLead: Boolean? = null
    val cityId: String? = null
    val countryId: String? = null
    val logoId: String? = null
    val coverId: String? = null
}