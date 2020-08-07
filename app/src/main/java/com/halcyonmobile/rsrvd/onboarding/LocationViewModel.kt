package com.halcyonmobile.rsrvd.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.core.user.UserRepository

enum class RetrieveState {
    PRE, POST
}

class LocationViewModel : ViewModel() {
    private val _location: MutableLiveData<Location> = MutableLiveData()
    private val _updateState: MutableLiveData<Boolean> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _interests: MutableLiveData<List<Interests>> = MutableLiveData()
    private val _retrieving: MutableLiveData<RetrieveState> = MutableLiveData(RetrieveState.PRE)

    val location: LiveData<Location> = _location
    val updateState: LiveData<Boolean> = _updateState
    val errorMessage: LiveData<String> = _errorMessage
    val interests: LiveData<List<Interests>> = _interests
    val retrieving: LiveData<RetrieveState> = _retrieving

    fun setLocation(newLocation: Location?, save: Boolean? = null) {
        _location.value = newLocation

        if (save == true && newLocation != null) {
            UserLocalRepository.location = Pair(newLocation.latitude, newLocation.longitude)
        }
    }

    fun onReadyClick(interests: List<Interests>): Boolean =
        if (_location.value == null || interests.isEmpty()) {
            _updateState.value = false
            _errorMessage.value = (if (_location.value == null) "Missing location" else "") +
                    (if (_location.value == null && interests.isEmpty()) " & " else "") +
                    if (interests.isEmpty()) "No interests" else ""
            false
        } else {
            if (_location.value != null) {
                UserRepository.update(_location.value!!, ArrayList(interests)) { _updateState.value = it }
                UserLocalRepository.location = Pair(_location.value!!.latitude, _location.value!!.longitude)
            }
            true
        }

    init {
        UserRepository.get {
            _interests.value = it?.interests
            _location.value = it?.location
            _retrieving.value = RetrieveState.POST
        }
    }

    fun clearLocation() {
        _location.value = null
    }
}