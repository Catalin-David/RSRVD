package com.halcyonmobile.rsrvd.core.venue.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Open (
    @Json(name = "0")
    val dayZero: DailyOpenHours,
    @Json(name = "1")
    val dayOne: DailyOpenHours,
    @Json(name = "2")
    val dayTwo: DailyOpenHours,
    @Json(name = "3")
    val dayThree: DailyOpenHours,
    @Json(name = "4")
    val dayFour: DailyOpenHours,
    @Json(name = "5")
    val dayFive: DailyOpenHours,
    @Json(name = "6")
    val daySix: DailyOpenHours
)