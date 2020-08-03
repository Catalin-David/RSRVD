package com.halcyonmobile.rsrvd.explorevenues

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository

class ExploreViewModel : ViewModel() {
    private val _searching: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _searchResults: MutableLiveData<List<Card>> = MutableLiveData()
    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(NoRecentCard.instance))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _error = MutableLiveData(false)
    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()

    val searching: LiveData<Boolean> = _searching
    val searchResults: LiveData<List<Card>> = _searchResults
    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error
    val cardInFocus: LiveData<Card> = _cardInFocus

    fun setCardInFocus(newCard: Card) {
        _cardInFocus.value = newCard
    }

    init {
        VenuesRepository.getRecentlyVisitedVenues { venues, error ->
            _error.value = error
            (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
                _recentlyVisitedCards.value = if (it == null || it.isEmpty()) listOf(NoRecentCard.instance) else it
            }
        }

        VenuesRepository.getExploreVenues { venues, error ->
            _error.value = error
            (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
                _exploreCards.value = it
            }
        }

        _cardInFocus.value = _recentlyVisitedCards.value!![0]
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

    fun searchTermChanged(term: String) {
        _searching.value = term.isNotEmpty()

        if (term.isNotEmpty()) {
            VenuesRepository.search(term) { venues, error ->
                _error.value = error
                (venues?.map { venue ->
                    Card(title = venue.name, image = venue.image, location = venue.location)
                }).let { _searchResults.value = it }
            }
        } else {
            _searchResults.value = listOf()
        }
    }
}
