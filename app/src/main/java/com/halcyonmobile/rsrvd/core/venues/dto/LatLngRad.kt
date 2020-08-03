package com.halcyonmobile.rsrvd.core.venues.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatLngRad (
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "radius")
    val radius: Double,
    @Json(name = "availability")
    val availability: Availability
)