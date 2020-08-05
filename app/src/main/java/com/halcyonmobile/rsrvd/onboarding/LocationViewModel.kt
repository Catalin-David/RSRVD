package com.halcyonmobile.rsrvd.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.me.MeRepository
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.shared.repository.LocalUserRepository

enum class RetrieveState {
    PRE, POST
}

class LocationViewModel : ViewModel() {
    private val meRepository: MeRepository = MeRepository()

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

    fun setLocation(newLocation: Location) {
        _location.value = newLocation
        LocalUserRepository.location = Pair(newLocation.latitude, newLocation.longitude)
    }

    fun onReadyClick(interests: List<Interests>): Boolean =
        if (_location.value == null || interests.isEmpty()) {
            _updateState.value = false
            _errorMessage.value = (if (_location.value == null) "Missing location" else "") +
                    (if (_location.value == null && interests.isEmpty()) " & " else "") +
                    if (interests.isEmpty()) "No interests" else ""
            false
        } else {
            meRepository.update(_location.value!!, ArrayList(interests)) { _updateState.value = it }
            LocalUserRepository.location = Pair(_location.value!!.latitude, _location.value!!.longitude)
            true
        }

    init {
        meRepository.get {
            _interests.value = it?.interests
            _location.value = it?.location
            if (it?.location?.latitude != null) {
                LocalUserRepository.location = Pair(it.location.latitude, it.location.longitude)
            }

            _retrieving.value = RetrieveState.POST
        }
    }
}