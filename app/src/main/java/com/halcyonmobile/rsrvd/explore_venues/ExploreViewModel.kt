package com.halcyonmobile.rsrvd.explore_venues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExploreViewModel : ViewModel() {
    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()

    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards

    fun setRecentlyVisitedCards(newCards: List<Card>) {
        _recentlyVisitedCards.value = newCards
    }

    val exploreCards: LiveData<List<Card>> = _exploreCards

    fun setExploreCards(newCards: List<Card>) {
        _exploreCards.value = newCards
    }
}