package com.halcyonmobile.rsrvd.onboarding

import android.os.Parcelable
import com.halcyonmobile.rsrvd.selectlocation.Location
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OnboardingData(
    val location: Location?,
    val interests: List<Interests>
) : Parcelable