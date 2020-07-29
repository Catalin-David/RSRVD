package com.halcyonmobile.rsrvd.feature.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.api.MeApi
import com.halcyonmobile.rsrvd.core.api.RetrofitManager
import com.halcyonmobile.rsrvd.core.api.dto.ProfileDto
import com.halcyonmobile.rsrvd.feature.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.iuliamariabirsan.core.repository.UserRepository

class LocationViewModel : ViewModel() {
    private val location: MutableLiveData<Location> = MutableLiveData()

    fun getLocation(): LiveData<Location> = location

    fun setLocation(newLocation: Location) {
        location.value = newLocation
    }

    fun onReadyClick() {
        getLocation().value?.let {
            RetrofitManager.retrofit
                .create(MeApi::class.java)
                .update(ProfileDto(location = it, interests = ArrayList(getInterests()), name = UserRepository.getName()))
                .enqueue(ProfileUpdateHandler())
        }
    }
}