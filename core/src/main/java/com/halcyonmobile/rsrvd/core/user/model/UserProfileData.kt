package com.halcyonmobile.rsrvd.core.user.model

import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location

data class UserProfileData(
    val location: Location? = null,
    val activitiesCompleted: Int = 0,
    val interests: List<Interests> = listOf()
)