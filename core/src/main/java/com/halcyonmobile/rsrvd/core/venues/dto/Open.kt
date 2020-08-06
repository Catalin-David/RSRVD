package com.halcyonmobile.rsrvd.core.venues.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Open(
    @Json(name = "0")
    val dayZero: StartEnd,
    @Json(name = "1")
    val dayOne: StartEnd,
    @Json(name = "2")
    val dayTwo: StartEnd,
    @Json(name = "3")
    val dayThree: StartEnd,
    @Json(name = "4")
    val dayFour: StartEnd,
    @Json(name = "5")
    val dayFive: StartEnd,
    @Json(name = "6")
    val daySix: StartEnd
)