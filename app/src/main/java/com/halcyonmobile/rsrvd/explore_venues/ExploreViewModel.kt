package com.halcyonmobile.rsrvd.explore_venues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository

class ExploreViewModel : ViewModel() {
    private val venuesRepository = VenuesRepository()

    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(NoRecentCard.instance))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _error = MutableLiveData(false)

    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error

    init {
        venuesRepository.getRecentlyVisitedVenues { venues, error ->
            _error.value = error
            (venues?.map { Card(title = it.name, image = it.image) }).let {
                _recentlyVisitedCards.value = if (it == null || it.isEmpty()) listOf(NoRecentCard.instance) else it
            }
        }

        venuesRepository.getExploreVenues { venues, error ->
            _error.value = error
            (venues?.map { Card(title = it.name, image = it.image) }).let {
                _exploreCards.value = it
            }
        }
    }
}