package com.iuliamariabirsan.core.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationDto(
    @Json(name = "idToken") val idToken: String
)