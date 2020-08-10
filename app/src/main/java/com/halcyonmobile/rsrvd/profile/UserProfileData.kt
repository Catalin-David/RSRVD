package com.halcyonmobile.rsrvd.profile

import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location

data class UserProfileData(
    val location: Location?,
    val activitiesCompleted: Int = 0,
    val interests: List<Interests> = listOf()
)