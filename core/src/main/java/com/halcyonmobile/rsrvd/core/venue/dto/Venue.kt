package com.halcyonmobile.rsrvd.core.venue.dto

import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.shared.Facilities
import com.halcyonmobile.rsrvd.core.venue.dto.Open
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Venue(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "location")
    val location: Location,
    @Json(name = "open")
    val open: Open,
    @Json(name = "facilities")
    val facilities: List<Facilities>
)