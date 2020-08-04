package com.halcyonmobile.rsrvd.core.venues.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterLocation(
    @Json(name = "latitude")
    private val latitude: Double,
    @Json(name = "longitude")
    private val longitude: Double,
    @Json(name = "radius")
    private val radius: Double
)
