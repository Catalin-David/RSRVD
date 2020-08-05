package com.halcyonmobile.rsrvd.core.model

data class UserProfileData(
    val location: Location? = null,
    val activitiesCompleted: Int = 0,
    val interests: List<Interests> = listOf()
)