package com.halcyonmobile.rsrvd.explorevenues

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.repository.LocalUserRepository
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository

class ExploreViewModel : ViewModel() {
    private val venuesRepository =
        VenuesRepository()

    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(StaticCards.noRecents))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _errorRecentlyVisited = MutableLiveData(false)
    private val _errorExplore = MutableLiveData(false)
    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()
    private val _noRecents: MutableLiveData<Boolean> = MutableLiveData(true)

    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val errorRecentlyVisited: LiveData<Boolean> = _errorRecentlyVisited
    val errorExplore: LiveData<Boolean> = _errorExplore
    val cardInFocus: LiveData<Card> = _cardInFocus
    val noRecents: LiveData<Boolean> = _noRecents

    fun setCardInFocus(newCard: Card?) {
        _cardInFocus.value = newCard
    }

    init {
        venuesRepository.getRecentlyVisitedVenues { venues, error ->
            if (error) {
                _errorRecentlyVisited.value = error
                _recentlyVisitedCards.value = listOf(StaticCards.noRecents)
                _noRecents.value = true
            } else {
                (venues?.map { Card(idVenue = it.id, title = it.name, image = it.image, location = it.location) }).let {
                    if (it != null && it.isNotEmpty()) {
                        _recentlyVisitedCards.value = it
                        _noRecents.value = false
                    } else {
                        _recentlyVisitedCards.value = listOf(StaticCards.noRecents)
                        _noRecents.value = true
                    }
                }
            }
        }

        venuesRepository.getExploreVenues { venues, error ->
            if (error) {
                _errorExplore.value = error
                _exploreCards.value = listOf(StaticCards.noExplore)
            } else {
                (venues?.map { Card(idVenue = it.id, title = it.name, image = it.image, location = it.location) }).let {
                    _exploreCards.value = if (it != null && it.isNotEmpty()) it else listOf(StaticCards.noExplore)
                }
            }
        }

        _cardInFocus.value = if (_recentlyVisitedCards.value == null || _recentlyVisitedCards.value!!.isEmpty()) null else _recentlyVisitedCards.value!![0]
    }

    fun getFormattedDistance(): String {
        _cardInFocus.value?.location?.let {
            val distances = FloatArray(1)
            Location.distanceBetween(it.latitude, it.longitude, LocalUserRepository.location.first, LocalUserRepository.location.second, distances)
            println("-------------------------------------------------${it.latitude}--${it.longitude}--${LocalUserRepository.location.first}--${LocalUserRepository.location.second}--${distances[0]}")
            val distanceFormatted = if (distances[0] > 1000) "${"%.2f".format(distances[0] / 1000)}km" else "${distances[0]}m"
            return " / $distanceFormatted away"
        }

        return ""
    }
}
