package com.halcyonmobile.rsrvd.explorevenues.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.venues.models.FilterLocation
import com.halcyonmobile.rsrvd.core.venues.models.StartEnd
import com.halcyonmobile.rsrvd.onboarding.Interests
import com.halcyonmobile.rsrvd.selectlocation.Location

class FilterViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    private val _location = MutableLiveData<FilterLocation>()
    private val _activities = MutableLiveData<List<Interests>>()
    private val _availability = MutableLiveData<StartEnd>()

    val name: LiveData<String> = _name
    val location: LiveData<FilterLocation> = _location
    val activities: LiveData<List<Interests>> = _activities
    val availability: LiveData<StartEnd> = _availability

    fun setLocation(myLocation: Location) {
        _location.value = FilterLocation(myLocation.latitude, myLocation.longitude, 3.0)
    }

    // TODO activities from the FlexBox
    fun isReady(): Boolean = _name.value != null || _location.value != null || !_activities.value.isNullOrEmpty() || _availability.value != null || true
}