package com.iuliamariabirsan.core.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AuthenticationDto {
    @Json(name = "idToken")
    var idToken: String? = null
}