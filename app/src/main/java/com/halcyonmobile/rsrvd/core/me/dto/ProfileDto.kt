package com.halcyonmobile.rsrvd.core.me.dto

import com.halcyonmobile.rsrvd.core.model.Interests
import com.halcyonmobile.rsrvd.core.model.Location
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "location")
    val location: Location,
    @Json(name = "interests")
    val interests: List<Interests>
)