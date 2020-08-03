package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.me.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.shared.repository.UserRepository
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager

internal class MeRemoteSource {
    private val meApi = RetrofitManager.retrofitWithAuthorication.create(MeApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) {
        meApi.update(ProfileDto(UserRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
    }
}