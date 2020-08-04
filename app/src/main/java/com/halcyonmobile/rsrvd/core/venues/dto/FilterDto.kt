package com.halcyonmobile.rsrvd.core.venues.dto

import com.halcyonmobile.rsrvd.core.venues.models.StartEnd
import com.halcyonmobile.rsrvd.core.venues.models.FilterLocation
import com.halcyonmobile.rsrvd.onboarding.Interests
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "location")
    val location: FilterLocation,
    @Json(name = "activities")
    val activities: List<Interests>,
    @Json(name = "availability")
    val availability: StartEnd
)