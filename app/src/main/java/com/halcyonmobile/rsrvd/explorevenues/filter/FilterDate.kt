package com.halcyonmobile.rsrvd.explorevenues.filter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterDate(
    val year: Int,
    val month: Int,
    val day: Int
) : Parcelable