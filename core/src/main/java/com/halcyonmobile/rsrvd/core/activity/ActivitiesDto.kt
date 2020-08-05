package com.halcyonmobile.rsrvd.core.activity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActivitiesDto (
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "max")
    val max: Int,
    @Json(name = "price")
    val price: PriceDto
)