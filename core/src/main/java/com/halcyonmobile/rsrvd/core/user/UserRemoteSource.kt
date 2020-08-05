package com.halcyonmobile.rsrvd.core.user

import com.halcyonmobile.rsrvd.core.user.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.shared.repository.UserRepository
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager

internal class UserRemoteSource {
    private val userApi = RetrofitManager.retrofitWithAuthentication!!.create(UserApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) {
        userApi.update(ProfileDto(UserRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
    }
}