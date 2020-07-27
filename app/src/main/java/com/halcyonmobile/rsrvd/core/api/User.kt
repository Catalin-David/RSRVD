package com.halcyonmobile.rsrvd.core.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User (
    val id: String,
    val email: String,
    val name: String,
    val image: String
)