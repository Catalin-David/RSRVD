package com.halcyonmobile.rsrvd.profile

import com.halcyonmobile.rsrvd.onboarding.Interests
import com.halcyonmobile.rsrvd.selectlocation.Location

data class UserProfileData(
    val location: Location? = null,
    val activitiesCompleted: Int = 0,
    val interests: List<Interests> = listOf()
)