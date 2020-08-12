package com.halcyonmobile.rsrvd.core.reservation.dto

import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActivityDto(
    @Json(name = "id") val id: String,
    @Json(name = "venue") val venue: Venue,
    @Json(name = "name") val name: String,
    @Json(name = "max") val max: Int,
    @Json(name = "price") val price: PriceDto
)