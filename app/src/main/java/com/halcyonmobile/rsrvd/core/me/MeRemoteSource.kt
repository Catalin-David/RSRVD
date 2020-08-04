package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.me.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.me.dto.UserDto
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.onboarding.Interests
import com.halcyonmobile.rsrvd.selectlocation.Location
import retrofit2.Call
import retrofit2.Callback

class MeRemoteSource {
    private val meApi = RetrofitManager.retrofit.create(MeApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) {
        meApi.update(ProfileDto(UserRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
    }

    fun get(updateState: (UserDto?) -> Unit) {
        meApi.get().enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: retrofit2.Response<UserDto>) {
                updateState(response.body())
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                updateState(null)
            }
        })
    }
}