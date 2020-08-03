package com.halcyonmobile.rsrvd.core.venues.dto

import com.halcyonmobile.rsrvd.selectlocation.Location
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchVenueBodyDto (
    @Json(name = "name")
    val name: String,
    @Json(name = "location")
    val location: LatLngRad? = null,
    @Json(name = "activities")
    val activities: List<String>? = null,
    @Json(name = "availability")
    val availability: Availability? = null
)