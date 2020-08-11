package com.halcyonmobile.rsrvd.core.venues.dto

import android.os.Parcelable
import com.halcyonmobile.rsrvd.core.activity.ActivitiesDto
import com.halcyonmobile.rsrvd.core.shared.Facilities
import com.halcyonmobile.rsrvd.core.shared.Location
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class VenueById (
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
    val facilities: List<Facilities>,
    @Json(name = "activities")
    val activities: List<ActivitiesDto>
) : Parcelable