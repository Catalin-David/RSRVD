package com.halcyonmobile.rsrvd.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location

class LocationViewModel : ViewModel() {
    private val _location: MutableLiveData<Location> = MutableLiveData()
    private val _updateState: MutableLiveData<Boolean> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    val location: LiveData<Location> = _location
    val updateState: LiveData<Boolean> = _updateState
    val errorMessage: LiveData<String> = _errorMessage

    fun setLocation(newLocation: Location) {
        _location.value = newLocation
    }

    fun onReadyClick(interests: List<Interests>) {
        if (_location.value == null || interests.isEmpty()) {
            _updateState.value = false
            _errorMessage.value = (if (_location.value == null) "Missing location" else "") +
                    (if (_location.value == null && interests.isEmpty()) " & " else "") +
                    if (interests.isEmpty()) "No interests" else ""
        } else {
            UserRepository.update(_location.value!!, ArrayList(interests)) { _updateState.value = it }
            UserRepository.location = Pair(_location.value!!.latitude, _location.value!!.longitude)
        }
    }
}