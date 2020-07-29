package com.halcyonmobile.rsrvd.feature.editprofile

import android.view.View
import com.halcyonmobile.rsrvd.core.api.dto.UserDto
import com.halcyonmobile.rsrvd.feature.utils.showSnackbar
import retrofit2.Call
import retrofit2.Callback

class ProfileUpdateHandler(private val view: View?) : Callback<UserDto> {
    override fun onResponse(call: Call<UserDto>, response: retrofit2.Response<UserDto>) {
        if (response.code() == 200) {
            view?.showSnackbar("Preferences saved")?.show()
        } else {
            view?.showSnackbar("Preferences NOT saved")?.show()
        }
    }

    override fun onFailure(call: Call<UserDto>, t: Throwable) {
        view?.showSnackbar("Something went wrong")?.show()
    }
}