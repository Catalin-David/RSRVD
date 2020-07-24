package com.halcyonmobile.rsrvd.feature.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.feature.selectlocation.Location

class OnboardingViewModel : ViewModel() {
    private val location: MutableLiveData<Location> = MutableLiveData()

    fun getLocation(): LiveData<Location> = location

    fun setLocation(newLocation: Location) {
        location.value = newLocation
    }
}