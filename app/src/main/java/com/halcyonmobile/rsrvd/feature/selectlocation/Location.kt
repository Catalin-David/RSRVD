package com.halcyonmobile.rsrvd.feature.selectlocation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Location(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val details: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val placeId: String? = null
) : Parcelable