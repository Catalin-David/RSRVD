package com.halcyonmobile.rsrvd.core.repository

import android.content.ContentValues
import android.util.Log
import com.halcyonmobile.rsrvd.core.RetrofitSingleton
import com.halcyonmobile.rsrvd.core.api.AuthenticationAPI
import com.halcyonmobile.rsrvd.core.dto.AuthenticationRequestDto
import com.halcyonmobile.rsrvd.core.dto.AuthenticationResponseDto
import com.halcyonmobile.rsrvd.core.sharedpreferences.SharedPreferencesManager
import com.halcyonmobile.rsrvd.core.dto.UserResponseDto
import com.halcyonmobile.rsrvd.profile.UserProfileData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    private val userRemoteSource = UserRemoteSource()
    // TODO set when looged in
    var name: String = "NAME"

    // TODO change when selected
    var location: Pair<Double, Double> = Pair(0.0, 0.0)

    var isUserLoggedIn: Boolean
        get() = SharedPreferencesManager.isUserLoggedIn
        set(userStatus) {
            SharedPreferencesManager.isUserLoggedIn = userStatus
        }

    fun userSignIn(idToken: String, onSuccess: (token: String) -> Unit, onFailure: () -> Unit) =
        RetrofitSingleton.get()
            .create(AuthenticationAPI::class.java)
            .postToken(AuthenticationRequestDto(idToken))
            .enqueue(object : Callback<AuthenticationResponseDto> {
                override fun onFailure(call: Call<AuthenticationResponseDto>, t: Throwable) {
                    Log.w(ContentValues.TAG, "error: ", t)
                    onFailure()
                }

                override fun onResponse(
                    call: Call<AuthenticationResponseDto>,
                    response: Response<AuthenticationResponseDto>
                ) {

                    val result = response.body()

                    if (!response.isSuccessful || result == null) {
                        onFailure()
                    } else {
                        onSuccess(result.accessToken)

                        Log.w(
                            ContentValues.TAG,
                            "code ${response.code()} ${result.accessToken}"
                        )
                    }
                }
            })
    fun getUserProfileData(): UserProfileData{
        val response = userRemoteSource.getSignedInUserInformation()
        response?.let {
            return UserProfileData(response.location, response.reservations, response.interests)
        }
        return UserProfileData()
    }
}