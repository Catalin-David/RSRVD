package com.halcyonmobile.rsrvd.core.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserResponseDto(
    @Json(name = "id") val _id: String,
    @Json(name = "email") val _email: String,
    @Json(name = "name") val _name: String,
    @Json(name = "image") val _image: String,
    @Json(name = "provider") val _provider: Any,
    @Json(name = "interests") val _interests: List<String>,
    @Json(name = "location") val _location: Any,
    @Json(name = "reservations") val _reservations: Int
)