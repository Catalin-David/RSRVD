package com.halcyonmobile.rsrvd.venuedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.Facilities
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById

class VenueDetailViewModel : ViewModel() {
    private val _listActivities: MutableLiveData<List<Interests>> = MutableLiveData()
    private val _hashMapActivities: MutableLiveData<Map<Interests, String>> = MutableLiveData()
    private val _listFacilities: MutableLiveData<List<Facilities>> = MutableLiveData()
    private val _venue: MutableLiveData<VenueById> = MutableLiveData()

    val venue: LiveData<VenueById> = _venue
    val listActivities: LiveData<List<Interests>> = _listActivities
    val listFacilities: LiveData<List<Facilities>> = _listFacilities
    val hashMapActivities: LiveData<Map<Interests, String>> = _hashMapActivities

    fun getVenue(venueId: String) {
        VenuesRepository.getVenueById(venueId,
            callback = {
                _venue.value = it
                _listActivities.value = it.activities.map { index -> Interests.getInterestBasedOnName(index.name) }.toList()
                _listFacilities.value = it.facilities
                _hashMapActivities.value = it.activities.map { index -> Interests.getInterestBasedOnName(index.name) to index.id }.toMap()
                println("------------------------------------------- ${_hashMapActivities.value}")
            })

    }
}