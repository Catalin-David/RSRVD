package com.halcyonmobile.rsrvd.core.shared

data class UserProfileData(
    val location: Location,
    val activitiesCompleted: Int = 0,
    val interests: List<Interests> = listOf()
)