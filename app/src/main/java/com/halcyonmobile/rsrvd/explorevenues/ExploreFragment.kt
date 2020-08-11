package com.halcyonmobile.rsrvd.explorevenues

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentExploreBinding
import com.halcyonmobile.rsrvd.makereservation.MakeReservationActivity
import com.halcyonmobile.rsrvd.explorevenues.filter.FilterActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar
import com.halcyonmobile.rsrvd.venuedetails.VenueDetailActivity

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)
        viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val searchResultsAdapter = CardsAdapter { openVenueDetails(it) }
        val recentlyViewedAdapter = CardsAdapter { openVenueDetails(it) }
        val exploreAdapter = CardsAdapter { openVenueDetails(it) }

        setUpObservers(searchResultsAdapter, recentlyViewedAdapter, exploreAdapter)
        setUpLists(searchResultsAdapter, recentlyViewedAdapter, exploreAdapter)

        binding.searchVenueBar.filterIcon.setOnClickListener {
            startActivityForResult(Intent(context, FilterActivity::class.java), FILTER_REQUEST_CODE)
        }

        binding.readMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.read_more_link))))
        }
    }

    private fun openVenueDetails(card: Card) {
        if (context != null && card.idVenue != null) {
            startActivity(VenueDetailActivity.getStartIntent(requireContext(), card.idVenue))
        }
    }

    private fun setUpObservers(searchResultsAdapter: CardsAdapter, recentlyViewedAdapter: CardsAdapter, exploreAdapter: CardsAdapter) {
        val handler = Handler()
        val runnable = Runnable { viewModel.search() }

        viewModel.apply {
            searchResults.observe(viewLifecycleOwner) {
                searchResultsAdapter.submitList(it)
                if (!searchResults.value.isNullOrEmpty()) {
                    setCardInFocus(searchResults.value?.get(0))
                }
            }
            recentlyVisitedCards.observe(viewLifecycleOwner) {
                recentlyViewedAdapter.submitList(if (it.isNotEmpty()) it else listOf(ExploreViewModel.NO_RECENTS_CARD))
            }
            exploreCards.observe(viewLifecycleOwner) {
                exploreAdapter.submitList(if (it.isNotEmpty()) it else listOf(ExploreViewModel.NO_CARDS))
            }

            error.observe(viewLifecycleOwner) { if (it) view?.showSnackbar(getString(R.string.something_went_wrong)) }

            searchTerm.observe(viewLifecycleOwner) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, DEBOUNCE_DURATION)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        linearLayoutManager: LinearLayoutManager,
        listAdapter: CardsAdapter,
        itemDecorator: MarginDecorator,
        snapHelper: Boolean? = null,
        scrollListener: View.OnScrollChangeListener? = null
    ) {
        recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = linearLayoutManager
            adapter = listAdapter
            addItemDecoration(itemDecorator)
            if (snapHelper == true) LinearSnapHelper().attachToRecyclerView(this)
            scrollListener?.let { setOnScrollChangeListener(scrollListener) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpLists(searchResultsAdapter: CardsAdapter, recentlyViewedAdapter: CardsAdapter, exploreAdapter: CardsAdapter) {
        // Search Results Setup
        setUpRecyclerView(
            binding.searchResults.searchResultsList,
            LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL },
            searchResultsAdapter,
            MarginDecorator(bottom = true)
        )

        val recyclerViewLayoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }

        // Recently Visited Setup
        setUpRecyclerView(
            binding.recentlyVisited.recentlyVisitedList,
            recyclerViewLayoutManager,
            recentlyViewedAdapter,
            MarginDecorator(right = true),
            snapHelper = true,
            scrollListener = View.OnScrollChangeListener { _, _, _, _, _ ->
                viewModel.recentlyVisitedCards.value?.isNotEmpty().let {
                    val firstVisiblePosition = recyclerViewLayoutManager.findFirstVisibleItemPosition()
                    if (firstVisiblePosition != -1 && !viewModel.recentlyVisitedCards.value.isNullOrEmpty()) {
                        viewModel.setCardInFocus(viewModel.recentlyVisitedCards.value?.get(firstVisiblePosition))
                    }
                }
            }
        )

        // Explore Setup
        setUpRecyclerView(
            binding.explore.exploreList,
            LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL },
            exploreAdapter,
            MarginDecorator(right = true),
            snapHelper = true
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && resultCode == Activity.RESULT_OK && requestCode == FILTER_REQUEST_CODE) {
            viewModel.setFilters(data.getParcelableExtra(FilterActivity.FILTERS))
            viewModel.search()
        }
    }

    companion object {
        const val DEBOUNCE_DURATION: Long = 500
        const val FILTER_REQUEST_CODE = 1
    }
}
