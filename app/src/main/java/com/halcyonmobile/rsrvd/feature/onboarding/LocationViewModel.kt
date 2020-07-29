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
    private val updateState: MutableLiveData<Boolean> = MutableLiveData()

    fun getLocation(): LiveData<Location> = location

    fun setLocation(newLocation: Location) {
        location.value = newLocation
    }

    fun getUpdateState(): LiveData<Boolean> = updateState

    private fun setUpdateState(newState: Boolean) {
        updateState.value = newState
    }

    fun onReadyClick(interests: List<Interests>) {
        location.value?.let { myLocation -> meRepository.update(myLocation, ArrayList(interests)) { setUpdateState(it) } }
    }
}