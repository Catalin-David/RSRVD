package com.halcyonmobile.rsrvd.core.venues.dto

import android.os.Parcelable
import com.halcyonmobile.rsrvd.core.shared.dto.PriceDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ActivitiesDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "max")
    val max: Int,
    @Json(name = "price")
    val price: PriceDto
) : Parcelable