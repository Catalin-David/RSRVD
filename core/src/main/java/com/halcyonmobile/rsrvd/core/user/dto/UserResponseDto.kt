package com.halcyonmobile.rsrvd.core.user.dto

import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseDto(
    @Json(name = "id") val id: String,
    @Json(name = "email") val email: String,
    @Json(name = "name") val name: String,
    @Json(name = "image") val image: String,
    @Json(name = "interests") val interests: List<Interests>,
    @Json(name = "location") val location: Location,
    @Json(name = "reservations") val reservations: Int
)