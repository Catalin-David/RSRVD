package com.halcyonmobile.rsrvd.feature.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.api.MeRepository
import com.halcyonmobile.rsrvd.feature.selectlocation.Location

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