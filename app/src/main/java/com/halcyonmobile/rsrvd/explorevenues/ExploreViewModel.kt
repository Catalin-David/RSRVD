package com.halcyonmobile.rsrvd.explorevenues

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository

class ExploreViewModel : ViewModel() {
    private val venuesRepository = VenuesRepository()

    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(NoRecentCard.instance))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _error = MutableLiveData(false)
    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()

    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error
    val cardInFocus: LiveData<Card> = _cardInFocus

    fun setCardInFocus(newCard: Card) {
        _cardInFocus.value = newCard
    }

    init {
        venuesRepository.getRecentlyVisitedVenues { venues, error ->
            _error.value = error
            (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
                _recentlyVisitedCards.value = if (it == null || it.isEmpty()) listOf(NoRecentCard.instance) else it
            }
        }

        venuesRepository.getExploreVenues { venues, error ->
            _error.value = error
            (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
                _exploreCards.value = it
            }

            (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
                _recentlyVisitedCards.value = if (it == null || it.isEmpty()) listOf(NoRecentCard.instance) else it
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
}
