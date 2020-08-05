package com.halcyonmobile.rsrvd.explorevenues

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository
import com.halcyonmobile.rsrvd.core.venues.dto.FilterDto
import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.halcyonmobile.rsrvd.explorevenues.filter.Filters

class ExploreViewModel : ViewModel() {
    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _error = MutableLiveData(false)
    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()
    private val _filters = MutableLiveData<Filters>()
    private val _filteredVenuesCards = MutableLiveData<List<Card>>()

    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error
    val cardInFocus: LiveData<Card> = _cardInFocus
    val filters: LiveData<Filters> = _filters

    val filtersApplied = MediatorLiveData<Boolean>().apply {
        addSource(_filters) { value = _filters.value != null }
    }

    fun clearFilters() {
        _filters.value = null
    }

    fun setFilters(filters: Filters?) {
        _filters.value = filters
    }

    fun setCardInFocus(newCard: Card?) {
        _cardInFocus.value = newCard ?: NoRecentCard.instance
    }

    init {
        VenuesRepository.getRecentlyVisitedVenues { venues, error -> storeVenuesAsCardsIn(_recentlyVisitedCards, venues, error) }

        if (_recentlyVisitedCards.value.isNullOrEmpty()) {
            _recentlyVisitedCards.value = listOf(NoRecentCard.instance)
        }

        VenuesRepository.getExploreVenues { venues, error ->
            storeVenuesAsCardsIn(_exploreCards, venues, error)
        }
    }

    fun getFormattedDistance(): String {
        _cardInFocus.value?.location?.let {
            val distances = FloatArray(1)
            Location.distanceBetween(it.latitude, it.longitude, UserRepository.location.first, UserRepository.location.second, distances)
            val distanceFormatted = if (distances[0] > 1000) "${"%.2f".format(distances[0] / 1000)}km" else "${distances[0]}m"
            return " / $distanceFormatted away"
        }

        return ""
    }

    private fun storeVenuesAsCardsIn(storage: MutableLiveData<List<Card>>, venues: List<Venue>?, error: Boolean) {
        _error.value = error

        (venues?.map { Card(title = it.name, image = it.image, location = it.location) })?.let {
            storage.value = it
        }
    }

    fun filterVenues() {
        VenuesRepository.filterVenues(
            FilterDto(
                name = filters.value?.name,
                location = filters.value?.location,
                activities = filters.value?.activities,
                availability = filters.value?.availability
            )
        ) { venues, error ->
            storeVenuesAsCardsIn(_filteredVenuesCards, venues, error)
            println(_filteredVenuesCards.value)
        }
    }
}
