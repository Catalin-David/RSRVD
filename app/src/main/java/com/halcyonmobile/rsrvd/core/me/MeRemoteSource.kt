package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.me.dto.ProfileDto
import com.halcyonmobile.rsrvd.feature.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.feature.onboarding.Interests
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.iuliamariabirsan.core.repository.UserRepository

class MeRemoteSource {
    private val meApi: MeApi = RetrofitManager.retrofit.create(MeApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) {
        meApi.update(ProfileDto(UserRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
    }
}