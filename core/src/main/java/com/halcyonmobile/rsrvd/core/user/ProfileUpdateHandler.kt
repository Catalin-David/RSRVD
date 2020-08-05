package com.halcyonmobile.rsrvd.core.user

import com.halcyonmobile.rsrvd.core.user.dto.UserDto
import retrofit2.Call
import retrofit2.Callback

internal class ProfileUpdateHandler(private val updateState: (Boolean) -> Unit) : Callback<UserDto> {
    override fun onResponse(call: Call<UserDto>, response: retrofit2.Response<UserDto>) {
        updateState(response.code() == 200)
    }

    override fun onFailure(call: Call<UserDto>, t: Throwable) {
        updateState(false)
    }
}