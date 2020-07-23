package com.iuliamariabirsan.core.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AuthenticationResponseDto {
    @Json(name = "accessToken")
    var accessToken: String? = null

    @Json(name = "refreshToken")
    var refreshToken: String? = null
}