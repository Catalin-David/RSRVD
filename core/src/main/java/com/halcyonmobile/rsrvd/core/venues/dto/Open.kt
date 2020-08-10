package com.halcyonmobile.rsrvd.core.venues.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Open(
    @Json(name = "0")
    val dayZero: StartEndHours,
    @Json(name = "1")
    val dayOne: StartEndHours,
    @Json(name = "2")
    val dayTwo: StartEndHours,
    @Json(name = "3")
    val dayThree: StartEndHours,
    @Json(name = "4")
    val dayFour: StartEndHours,
    @Json(name = "5")
    val dayFive: StartEndHours,
    @Json(name = "6")
    val daySix: StartEndHours
)