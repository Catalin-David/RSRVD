package com.halcyonmobile.rsrvd.core.api

import com.halcyonmobile.rsrvd.feature.onboarding.Interests
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUserBody(val location: Location, val interests: List<Interests>)