package com.halcyonmobile.rsrvd.core.user.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "image")
    val image: String
)