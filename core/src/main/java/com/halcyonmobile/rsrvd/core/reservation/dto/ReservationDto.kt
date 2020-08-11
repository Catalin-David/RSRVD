package com.halcyonmobile.rsrvd.core.reservation.dto

import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState
import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReservationDto(
    @Json(name = "id") val id: String,
    @Json(name = "venue") val venue: Venue,
    @Json(name = "activity") val activity: ActivityDto,
    @Json(name = "start") val date: String,
    @Json(name = "end") val endDate: String,
    @Json(name = "createdBy") val creator: String,
    @Json(name = "status") val state: ReservationState
)