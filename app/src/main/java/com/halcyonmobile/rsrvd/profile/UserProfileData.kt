package com.halcyonmobile.rsrvd.profile

import com.halcyonmobile.rsrvd.onboarding.Interests

data class UserProfileData(
    val location: String,
    val activitiesCompleted: Int,
    val interests: List<Interests>
)