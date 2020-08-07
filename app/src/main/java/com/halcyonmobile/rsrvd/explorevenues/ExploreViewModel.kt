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
    private val _searchResultsCards: MutableLiveData<List<Card>> = MutableLiveData(emptyList())
    private val _recentlyVisitedCards: MutableLiveData<List<Card>> = MutableLiveData(emptyList())
    private val _exploreCards: MutableLiveData<List<Card>> = MutableLiveData(emptyList())
    private val _error = MutableLiveData(false)
    private val _cardInFocus: MutableLiveData<Card> = MutableLiveData()
    private val _filters = MutableLiveData<Filters>()

    val searching: LiveData<Boolean> = _searching
    val searchResults: LiveData<List<Card>> = _searchResultsCards
    val recentlyVisitedCards: LiveData<List<Card>> = _recentlyVisitedCards
    val exploreCards: LiveData<List<Card>> = _exploreCards
    val error: LiveData<Boolean> = _error
    val cardInFocus: LiveData<Card> = _cardInFocus
    val searchTerm: MutableLiveData<String> = MutableLiveData("")

    val filtersApplied = MediatorLiveData<Boolean>().apply {
        addSource(_filters) { value = _filters.value != null }
    }

    fun clearFilters() {
        _filters.value = null
        _searching.value = false
        notifyFiltersChanged()
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
        addSource(_searchResultsCards) { value = checkNoResults(isSearchResultEmpty = it?.isEmpty() ?: true) }
    }

    val searchResultsVisible = MediatorLiveData<Boolean>().apply {
        addSource(_searching) { value = checkSearchResults(isSearching = it) }
        addSource(_searchResultsCards) { value = checkSearchResults(isSearchResultEmpty = it?.isEmpty() ?: true) }
    }

    init {
        VenuesRepository.getRecentlyVisitedVenues { venues, error -> storeVenuesAsCardsIn(_recentlyVisitedCards, venues, error) }
        VenuesRepository.getExploreVenues { venues, error -> storeVenuesAsCardsIn(_exploreCards, venues, error) }
    }

    private fun checkNoResults(
        isLoading: Boolean = _loading.value ?: false,
        isSearching: Boolean = _searching.value ?: false,
        isSearchResultEmpty: Boolean = _searchResultsCards.value?.isEmpty() ?: false
    ) = !isLoading && isSearching && isSearchResultEmpty

    private fun checkSearchResults(
        isSearching: Boolean = _searching.value ?: false,
        isSearchResultEmpty: Boolean = _searchResultsCards.value?.isEmpty() ?: true
    ) = isSearching && !isSearchResultEmpty

    fun clear() {
        searchTerm.value = ""
    }

    fun searchTermChanged() {
        _searching.value = searchTerm.value?.isNotEmpty()

        if (_searching.value == true) {
            _loading.value = true
            VenuesRepository.filterVenues(
                FilterDto(
                    name = searchTerm.value,
                    location = _filters.value?.location,
                    activities = _filters.value?.activities,
                    availability = _filters.value?.availability
                )
            ) { venues, error -> storeVenuesAsCardsIn(_searchResultsCards, venues, error) }
            _loading.value = false
        } else {
            _searchResultsCards.value = listOf()
        }
    }

    fun notifyFiltersChanged() = searchTermChanged()

    private fun storeVenuesAsCardsIn(storage: MutableLiveData<List<Card>>?, venues: List<Venue>?, error: Boolean?) {
        _error.value = error

        if (error == false) {
            storage?.value = venues?.map { Card(title = it.name, image = it.image, location = it.location) }
        }
    }

    companion object {
        val NO_RECENTS_CARD = Card(title = "No activity yet. But it looks like it’s time for some!")
        val NO_CARDS = Card(title = "No venues found!")
    }
}
