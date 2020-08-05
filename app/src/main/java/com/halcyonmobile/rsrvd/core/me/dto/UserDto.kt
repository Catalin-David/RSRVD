package com.halcyonmobile.rsrvd.core.me.dto

import com.halcyonmobile.rsrvd.core.model.Interests
import com.halcyonmobile.rsrvd.core.model.Location
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
    val image: String,
    @Json(name = "interests")
    val interests: List<Interests>,
    @Json(name = "location")
    val location: Location
)