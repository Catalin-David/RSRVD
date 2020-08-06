package com.halcyonmobile.rsrvd.explorevenues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.venues.VenuesRepository
import com.halcyonmobile.rsrvd.core.venues.dto.FilterDto
import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.halcyonmobile.rsrvd.explorevenues.filter.Filters

class ExploreViewModel : ViewModel() {
    private val _searching: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _searchResults: MutableLiveData<List<Card>> = MutableLiveData(emptyList())

    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(listOf(NO_RECENTS_CARD))
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData()

    private val _error = MutableLiveData(false)

    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()
    private val _filters = MutableLiveData<Filters>()
    private val _filteredVenuesCards = MutableLiveData<List<Card>>()

    val searching: LiveData<Boolean> = _searching
    val searchResults: LiveData<List<Card>> = _searchResults
    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error
    val cardInFocus: LiveData<Card> = _cardInFocus
    val filters: LiveData<Filters> = _filters
    val searchTerm: MutableLiveData<String> = MutableLiveData("")

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
        _cardInFocus.value = newCard ?: NO_RECENTS_CARD
    }

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
        VenuesRepository.getRecentlyVisitedVenues { venues, error -> storeVenuesAsCardsIn(_recentlyVisitedCards, venues, error) }

        if (_recentlyVisitedCards.value.isNullOrEmpty()) {
            _recentlyVisitedCards.value = listOf(NO_RECENTS_CARD)
        }

        VenuesRepository.getExploreVenues { venues, error ->
            storeVenuesAsCardsIn(_exploreCards, venues, error)
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

    private fun storeVenuesAsCardsIn(storage: MutableLiveData<List<Card>>, venues: List<Venue>?, error: Boolean?) {
        _error.value = error
        storage.value =
            if (venues.isNullOrEmpty()) listOf(NO_RECENTS_CARD) else venues.map { Card(title = it.name, image = it.image, location = it.location) }
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
        }
    }
}
