package com.halcyonmobile.rsrvd.venuedetails

import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById

class VenueDetailViewModel : ViewModel() {
    fun getVenue(venueId: String, callback: (VenueById) -> Unit) = VenuesRepository.getVenueById(venueId, callback)
}