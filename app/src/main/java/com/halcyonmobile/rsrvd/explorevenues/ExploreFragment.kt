package com.halcyonmobile.rsrvd.explorevenues

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

        val searchResultsAdapter = CardsAdapter {
            startActivity(context?.let { intent ->
            it.idVenue.let { id ->
                VenueDetailActivity.getStartIntent(intent, id)
            }
        })}
        val recentlyViewedAdapter = CardsAdapter { /* TODO start activity to open Details */ }
        val exploreAdapter = CardsAdapter {card ->
            startActivity(context?.let { it -> VenueDetailActivity.getStartIntent(it, card.idVenue) })
        }

        setUpObservers(searchResultsAdapter, recentlyViewedAdapter, exploreAdapter)
        setUpLists(searchResultsAdapter, recentlyViewedAdapter, exploreAdapter)

        binding.readMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.read_more_link))))
        }
    }

    private fun setUpObservers(searchResultsAdapter: CardsAdapter, recentlyViewedAdapter: CardsAdapter, exploreAdapter: CardsAdapter) {
        val handler = Handler()
        val runnable = Runnable { viewModel.searchTermChanged() }

        viewModel.apply {
            searchResults.observe(viewLifecycleOwner) { searchResultsAdapter.submitList(it) }
            recentlyVisitedCards.observe(viewLifecycleOwner) { recentlyViewedAdapter.submitList(it) }
            exploreCards.observe(viewLifecycleOwner) { exploreAdapter.submitList(it) }

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
                    if (firstVisiblePosition != -1) {
                        viewModel.setCardInFocus(viewModel.recentlyVisitedCards.value!![firstVisiblePosition])
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

    companion object {
        const val DEBOUNCE_DURATION: Long = 500
    }
}
