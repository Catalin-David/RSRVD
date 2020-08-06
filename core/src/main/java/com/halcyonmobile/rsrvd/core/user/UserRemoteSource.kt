package com.halcyonmobile.rsrvd.core.user

import com.halcyonmobile.rsrvd.core.user.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.core.user.dto.UserDto
import retrofit2.Call
import retrofit2.Callback

internal class UserRemoteSource {
    private val userApi = RetrofitManager.retrofitWithAuthentication!!.create(UserApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) {
        userApi.update(ProfileDto(UserLocalRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
    }

    fun get(updateState: (UserDto?) -> Unit) {
        userApi.get().enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: retrofit2.Response<UserDto>) {
                updateState(response.body())
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                updateState(null)
            }
        })

    }
}