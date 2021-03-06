package com.halcyonmobile.rsrvd.core.shared

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(
    @Transient
    val id: UUID = UUID.randomUUID(),
    @Transient
    val placeId: String? = null,
    @Json(name = "city")
    val name: String = "no city found",
    @Json(name = "address")
    val details: String = "no address found",
    @Json(name = "latitude")
    val latitude: Double = 0.0,
    @Json(name = "longitude")
    val longitude: Double = 0.0
) : Parcelable