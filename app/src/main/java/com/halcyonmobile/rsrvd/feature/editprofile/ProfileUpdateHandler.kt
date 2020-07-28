package com.halcyonmobile.rsrvd.feature.editprofile

import android.content.Context
import android.widget.Toast
import com.halcyonmobile.rsrvd.core.api.dto.UserDto
import retrofit2.Call
import retrofit2.Callback

class ProfileUpdateHandler(private val context: Context) : Callback<UserDto> {
    override fun onResponse(call: Call<UserDto>, response: retrofit2.Response<UserDto>) {
        if (response.code() == 200) {
            Toast.makeText(context, "Preferences saved", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Preferences NOT saved", Toast.LENGTH_LONG).show()
        }
    }

    override fun onFailure(call: Call<UserDto>, t: Throwable) {
        Toast.makeText(context, "Something went horribly wrong! :O", Toast.LENGTH_LONG).show()
        println(t.message)
    }
}