package com.halcyonmobile.rsrvd.explorevenues.filter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterTime(
    val startHour: Int,
    val startMinute: Int,
    val finishHour: Int,
    val finishMinute: Int
) : Parcelable
