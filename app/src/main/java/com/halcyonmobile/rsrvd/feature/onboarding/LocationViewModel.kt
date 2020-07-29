package com.halcyonmobile.rsrvd.feature.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.api.MeApi
import com.halcyonmobile.rsrvd.core.api.MeRemoteSource
import com.halcyonmobile.rsrvd.core.api.MeRepository
import com.halcyonmobile.rsrvd.core.api.RetrofitManager
import com.halcyonmobile.rsrvd.core.api.dto.ProfileDto
import com.halcyonmobile.rsrvd.feature.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.iuliamariabirsan.core.repository.UserRepository

class LocationViewModel : ViewModel() {
    private val meRepository: MeRepository = MeRepository()
    private val location: MutableLiveData<Location> = MutableLiveData()

    fun getLocation(): LiveData<Location> = location

    fun setLocation(newLocation: Location) {
        location.value = newLocation
    }

    fun onReadyClick(interests: List<Interests>) {
        location.value?.let { meRepository.update(it, ArrayList(interests)) }
    }
}