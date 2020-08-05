package com.halcyonmobile.rsrvd.explorevenues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository

class ExploreViewModel : ViewModel() {
    private val _searching: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _searchResults: MutableLiveData<List<Card>> = MutableLiveData(emptyList())
    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(NoRecentCard.instance))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()
    private val _error = MutableLiveData(false)
    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)

    val searching: LiveData<Boolean> = _searching
    val searchResults: LiveData<List<Card>> = _searchResults
    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error
    val cardInFocus: LiveData<Card> = _cardInFocus

    val searchTerm: MutableLiveData<String> = MutableLiveData("")

    val noResultsVisible = MediatorLiveData<Boolean>()
    val searchResultsVisible = MediatorLiveData<Boolean>()

    init {
        noResultsVisibleSources()
        searchResultsVisibleSources()

        initializeRecentlyVisitedList()
        initializeExploreList()

        _cardInFocus.value = _recentlyVisitedCards.value?.get(0) ?: NoRecentCard.instance
    }

    private fun noResultsVisibleSources() {
        noResultsVisible.addSource(_loading) { noResultsVisible.value = checkNoResults() }
        noResultsVisible.addSource(_searching) { noResultsVisible.value = checkNoResults() }
        noResultsVisible.addSource(_searchResults) { noResultsVisible.value = checkNoResults() }
    }

    private fun searchResultsVisibleSources() {
        searchResultsVisible.addSource(_searching) { searchResultsVisible.value = checkSearchResults() }
        searchResultsVisible.addSource(_searchResults) { searchResultsVisible.value = checkSearchResults() }
    }

    private fun initializeRecentlyVisitedList() = VenuesRepository.getRecentlyVisitedVenues { venues, error ->
        _error.value = error
        (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
            _recentlyVisitedCards.value = if (it == null || it.isEmpty()) listOf(NoRecentCard.instance) else it
        }
    }

    private fun initializeExploreList() = VenuesRepository.getExploreVenues { venues, error ->
        _error.value = error
        (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
            _exploreCards.value = it
        }
    }

    private fun checkNoResults() = _loading.value != true && searching.value == true && searchResults.value?.isEmpty() ?: false

    private fun checkSearchResults() = _searching.value == true && _searchResults.value?.isNotEmpty() ?: false

    fun setCardInFocus(newCard: Card) {
        _cardInFocus.value = newCard
    }

    fun clear() {
        searchTerm.value = ""
    }

    fun searchTermChanged() {
        _searching.value = searchTerm.value?.isNotEmpty()

        if (_searching.value == true) {
            _loading.value = true
            VenuesRepository.search(searchTerm.value!!) { venues, error ->
                _error.value = error
                (venues?.map { venue ->
                    Card(title = venue.name, image = venue.image, location = venue.location)
                }).let { _searchResults.value = it }
                if (venues.isNullOrEmpty()) {
                    _searchResults.value = emptyList()
                }
            }
            _loading.value = false
        } else {
            _searchResults.value = listOf()
        }
    }
}
