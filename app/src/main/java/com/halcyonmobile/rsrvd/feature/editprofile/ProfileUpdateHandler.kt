package com.halcyonmobile.rsrvd.feature.editprofile

import com.halcyonmobile.rsrvd.core.api.dto.UserDto
import retrofit2.Call
import retrofit2.Callback

class ProfileUpdateHandler(private val updateState: (Boolean) -> Unit) : Callback<UserDto> {
    override fun onResponse(call: Call<UserDto>, response: retrofit2.Response<UserDto>) {
        updateState(response.code() == 200)
    }

    override fun onFailure(call: Call<UserDto>, t: Throwable) {
        updateState(false)
    }
}