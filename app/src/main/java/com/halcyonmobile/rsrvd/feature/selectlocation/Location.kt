package com.halcyonmobile.rsrvd.feature.selectlocation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Location (
    val id: UUID,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val details: String,
    val placeId: String?
) : Parcelable {
    override fun toString(): String {
        return name
    }
}