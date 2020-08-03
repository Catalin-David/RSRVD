package com.halcyonmobile.rsrvd.core.venues.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Availability(
    @Json(name = "start")
    val start: String,
    @Json(name = "end")
    val end: String
)