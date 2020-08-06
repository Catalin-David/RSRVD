package com.halcyonmobile.rsrvd.core.venues.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class StartEnd(
    @Json(name = "start")
    val start: Float,
    @Json(name = "end")
    val end: Float
) : Parcelable