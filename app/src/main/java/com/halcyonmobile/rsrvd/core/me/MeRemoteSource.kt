package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.me.dto.ProfileDto
import com.halcyonmobile.rsrvd.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.onboarding.Interests
import com.halcyonmobile.rsrvd.selectlocation.Location
import com.iuliamariabirsan.core.repository.UserRepository

class MeRemoteSource {
    private val meApi: MeApi = RetrofitManager.retrofit.create(MeApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) {
        meApi.update(ProfileDto(UserRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
    }
}