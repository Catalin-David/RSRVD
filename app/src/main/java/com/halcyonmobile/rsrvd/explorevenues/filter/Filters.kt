package com.halcyonmobile.rsrvd.explorevenues.filter

import android.os.Parcelable
import com.halcyonmobile.rsrvd.core.venues.models.FilterLocation
import com.halcyonmobile.rsrvd.core.venues.models.StartEnd
import com.halcyonmobile.rsrvd.onboarding.Interests
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filters(
    val name: String? = null,
    val location: FilterLocation? = null,
    val activities: List<Interests>? = null,
    val availability: StartEnd? = null
) : Parcelable