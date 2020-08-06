package com.halcyonmobile.rsrvd.explorevenues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository

class ExploreViewModel : ViewModel() {
    private val _searching: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _searchResults: MutableLiveData<List<Card>> = MutableLiveData(emptyList())

    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(NO_RECENTS_CARD))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()

    private val _error = MutableLiveData(false)

    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()

    val searching: LiveData<Boolean> = _searching
    val searchResults: LiveData<List<Card>> = _searchResults
    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error
    val cardInFocus: LiveData<Card> = _cardInFocus

    val searchTerm: MutableLiveData<String> = MutableLiveData("")

    val noResultsVisible = MediatorLiveData<Boolean>().apply {
        addSource(_loading) { value = checkNoResults(isLoading = it) }
        addSource(_searching) { value = checkNoResults(isSearching = it) }
        addSource(_searchResults) { value = checkNoResults(isSearchResultEmpty = it.isEmpty()) }
    }

    val searchResultsVisible = MediatorLiveData<Boolean>().apply {
        addSource(_searching) { value = checkSearchResults(isSearching = it) }
        addSource(_searchResults) { value = checkSearchResults(isSearchResultEmpty = it.isEmpty()) }
    }

    init {
        initializeRecentlyVisitedList()
        initializeExploreList()

        _cardInFocus.value = _recentlyVisitedCards.value?.get(0) ?: NO_RECENTS_CARD
    }

    private fun initializeRecentlyVisitedList() = VenuesRepository.getRecentlyVisitedVenues { venues, error ->
        _error.value = error
        (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
            _recentlyVisitedCards.value = if (it == null || it.isEmpty()) listOf(NO_RECENTS_CARD) else it
        }
    }

    private fun initializeExploreList() = VenuesRepository.getExploreVenues { venues, error ->
        _error.value = error
        (venues?.map { Card(title = it.name, image = it.image, location = it.location) }).let {
            _exploreCards.value = it
        }
    }

    private fun checkNoResults(
        isLoading: Boolean = _loading.value ?: false,
        isSearching: Boolean = _searching.value ?: false,
        isSearchResultEmpty: Boolean = _searchResults.value?.isEmpty() ?: false
    ) = !isLoading && isSearching && isSearchResultEmpty

    private fun checkSearchResults(
        isSearching: Boolean = _searching.value ?: false,
        isSearchResultEmpty: Boolean = _searchResults.value?.isEmpty() ?: true
    ) = isSearching && !isSearchResultEmpty

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

    companion object {
        private val NO_RECENTS_CARD = Card(title = "No activity yet. But it looks like it’s time for some!")
    }
}
