package com.halcyonmobile.rsrvd.feature.onboarding


import android.os.Parcelable
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OnboardingData (
    val location: Location?,
    val interests: List<Interests>
) : Parcelable