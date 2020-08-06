package com.halcyonmobile.rsrvd.core.venues.dto

import com.halcyonmobile.rsrvd.core.shared.Interests
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterDto(
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "location")
    val location: FilterLocation? = null,
    @Json(name = "activities")
    val activities: List<Interests>? = null,
    @Json(name = "availability")
    val availability: StartEnd? = null
)