package com.halcyonmobile.rsrvd.core.reservation.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReservationRequestDto(
    @Json(name = "activityId")
    val activityId: String,
    @Json(name = "start")
    val start: String,
    @Json(name = "end")
    val end: String
) : Parcelable