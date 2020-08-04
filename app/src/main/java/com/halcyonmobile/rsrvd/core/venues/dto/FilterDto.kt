package com.halcyonmobile.rsrvd.core.venues.dto

import com.halcyonmobile.rsrvd.core.venues.models.StartEnd
import com.halcyonmobile.rsrvd.core.venues.models.FilterLocation
import com.halcyonmobile.rsrvd.onboarding.Interests
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterDto(
    @Json(name = "name")
    private val name: String,
    @Json(name = "location")
    private val location: FilterLocation,
    @Json(name = "activities")
    private val activities: List<Interests>,
    @Json(name = "availability")
    private val availability: StartEnd
)