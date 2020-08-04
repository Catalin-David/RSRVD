package com.halcyonmobile.rsrvd.profile

import com.halcyonmobile.rsrvd.core.model.Interests
import com.halcyonmobile.rsrvd.core.model.Location

data class UserProfileData(
    val location: Location? = null,
    val activitiesCompleted: Int = 0,
    val interests: List<Interests> = listOf()
)