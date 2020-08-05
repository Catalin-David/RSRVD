package com.halcyonmobile.rsrvd.core.venue.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyOpenHours (
    @Json(name = "start")
    val start: Float,
    @Json(name = "end")
    val end: Float
)