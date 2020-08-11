package com.halcyonmobile.rsrvd.core.activity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PriceDto (
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "quantity")
    val quantity: Int,
    @Json(name = "unit")
    val unit: String
): Parcelable
