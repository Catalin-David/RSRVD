package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.me.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.model.Interests
import com.halcyonmobile.rsrvd.core.model.Location
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager

class MeRemoteSource {
    private val meApi = RetrofitManager.retrofit.create(MeApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) {
        meApi.update(ProfileDto(UserRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
    }
}