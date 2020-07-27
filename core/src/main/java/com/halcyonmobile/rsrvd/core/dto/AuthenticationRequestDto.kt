package com.halcyonmobile.rsrvd.core.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationRequestDto(
    @Json(name = "idToken") val idToken: String
)
