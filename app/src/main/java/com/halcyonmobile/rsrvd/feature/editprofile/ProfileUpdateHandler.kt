package com.halcyonmobile.rsrvd.feature.editprofile

import android.view.View
import com.halcyonmobile.rsrvd.core.api.dto.UserDto
import com.halcyonmobile.rsrvd.feature.shared.CustomView
import retrofit2.Call
import retrofit2.Callback

class ProfileUpdateHandler(private val view: View) : Callback<UserDto> {
    override fun onResponse(call: Call<UserDto>, response: retrofit2.Response<UserDto>) {
        if (response.code() == 200) {
            CustomView(view).createSnackbar("Preferences saved").show()
        } else {
            CustomView(view).createSnackbar("Preferences NOT saved").show()
        }
    }

    override fun onFailure(call: Call<UserDto>, t: Throwable) {
        CustomView(view).createSnackbar("Something went wrong").show()
    }
}