package com.halcyonmobile.rsrvd.explorevenues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExploreViewModel : ViewModel() {
    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(NoRecentCard.instance))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()

    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards

    fun setRecentlyVisitedCards(newCards: List<Card>) {
        _recentlyVisitedCards.value = if (newCards.isEmpty()) listOf(NoRecentCard.instance) else newCards
    }

    val exploreCards: LiveData<List<Card>> = _exploreCards

    fun setExploreCards(newCards: List<Card>) {
        _exploreCards.value = newCards
    }
}