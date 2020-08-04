package com.halcyonmobile.rsrvd.explorevenues.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository
import com.halcyonmobile.rsrvd.core.venues.dto.FilterDto
import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.halcyonmobile.rsrvd.core.venues.models.FilterLocation
import com.halcyonmobile.rsrvd.core.venues.models.StartEnd
import com.halcyonmobile.rsrvd.onboarding.Interests

class FilterViewModel : ViewModel() {
    private val _error = MutableLiveData<Boolean>(false)
    private val _filteredVenues = MutableLiveData<List<Venue>>(emptyList())

    private val _name = MutableLiveData<String>("a")
    private val _location = MutableLiveData<FilterLocation>()
    private val _activities = MutableLiveData<List<Interests>>()
    private val _availability = MutableLiveData<StartEnd>()

    fun filterVenues() {
        if (_name.value != null && _location.value != null && _activities.value != null && _availability.value != null) {
            VenuesRepository.filterVenues(
                FilterDto(
                    name = _name.value!!,
                    location = _location.value!!,
                    activities = _activities.value!!,
                    availability = _availability.value!!
                )
            ) { venues, error ->
                _error.value = error
                _filteredVenues.value = venues
            }
        }
    }
}