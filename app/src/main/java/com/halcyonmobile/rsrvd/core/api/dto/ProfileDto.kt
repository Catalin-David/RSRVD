package com.halcyonmobile.rsrvd.core.api.dto

import com.halcyonmobile.rsrvd.feature.onboarding.Interests
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.iuliamariabirsan.core.repository.UserRepository
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileDto(
    @Json(name = "name")
    val name: String = UserRepository.getName(),
    @Json(name = "location")
    val location: Location,
    @Json(name = "interests")
    val interests: List<Interests>
)