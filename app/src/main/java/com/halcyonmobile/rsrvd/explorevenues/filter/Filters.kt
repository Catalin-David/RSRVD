package com.halcyonmobile.rsrvd.explorevenues.filter

import android.os.Parcelable
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.venues.dto.FilterLocation
import com.halcyonmobile.rsrvd.core.venues.dto.StartEndHours
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filters(
    val name: String? = null,
    val location: FilterLocation? = null,
    val activities: List<Interests>? = null,
    val availability: StartEndHours? = null
) : Parcelable