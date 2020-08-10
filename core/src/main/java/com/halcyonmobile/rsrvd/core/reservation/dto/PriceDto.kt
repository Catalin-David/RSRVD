package com.halcyonmobile.rsrvd.core.reservation.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PriceDto(
    @Json(name = "amount") val amount: Int,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "unit") val unit: String
)
