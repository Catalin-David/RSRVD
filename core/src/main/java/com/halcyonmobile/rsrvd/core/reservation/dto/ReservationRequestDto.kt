package com.halcyonmobile.rsrvd.core.reservation.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReservationRequestDto(
    @Json(name = "activityId")
    val activityId: String,
    @Json(name = "start")
    val start: String,
    @Json(name = "end")
    val end: String
)