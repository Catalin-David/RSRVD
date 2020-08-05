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

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel

    private val handler = Handler()
    private val runnable = Runnable { viewModel.searchTermChanged() }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)
        viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

        binding.apply {
            viewModel = this@ExploreFragment.viewModel
            lifecycleOwner = activity
        }

        val searchResultsAdapter = CardsAdapter { /* TODO start activity to open Details */ }
        val recentlyViewedAdapter = CardsAdapter { /* TODO start activity to open Details */ }
        val exploreAdapter = CardsAdapter { /* TODO start activity to open Details */ }

        setUpObservers(searchResultsAdapter, recentlyViewedAdapter, exploreAdapter)
        setUpLists(searchResultsAdapter, recentlyViewedAdapter, exploreAdapter)

        binding.readMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.read_more_link))))
        }
    }

    private fun setUpObservers(searchResultsAdapter: CardsAdapter, recentlyViewedAdapter: CardsAdapter, exploreAdapter: CardsAdapter) {
        viewModel.apply {
            searchResults.observe(viewLifecycleOwner) { searchResultsAdapter.submitList(it) }
            recentlyVisitedCards.observe(viewLifecycleOwner) { recentlyViewedAdapter.submitList(it) }
            exploreCards.observe(viewLifecycleOwner) { exploreAdapter.submitList(it) }

            error.observe(viewLifecycleOwner) { if (it) view?.showSnackbar(getString(R.string.something_went_wrong)) }

            searchTerm.observe(viewLifecycleOwner) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, debounceDuration)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        listOrientation: Int,
        listAdapter: CardsAdapter,
        itemDecorator: MarginDecorator,
        scrollListener: View.OnScrollChangeListener? = null,
        snapHelper: Boolean? = null
    ) {
        recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context).apply { orientation = listOrientation }
            adapter = listAdapter
            addItemDecoration(itemDecorator)
            scrollListener?.let { setOnScrollChangeListener(scrollListener) }
            if (snapHelper == true) LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpLists(searchResultsAdapter: CardsAdapter, recentlyViewedAdapter: CardsAdapter, exploreAdapter: CardsAdapter) {
        // Search Results Setup
        setUpRecyclerView(
            binding.searchResults.searchResultsList,
            LinearLayoutManager.VERTICAL,
            searchResultsAdapter,
            MarginDecorator(context, bottom = true)
        )

        // Recently Visited Setup
        setUpRecyclerView(
            binding.recentlyVisited.recentlyVisitedList,
            LinearLayoutManager.HORIZONTAL,
            recentlyViewedAdapter,
            MarginDecorator(context, right = true),
            scrollListener = View.OnScrollChangeListener { _, _, _, _, _ ->
                viewModel.recentlyVisitedCards.value?.isNotEmpty().let {
                    val firstVisiblePosition = LinearLayoutManager(context).findFirstVisibleItemPosition()
                    if (firstVisiblePosition != -1) {
                        viewModel.setCardInFocus(viewModel.recentlyVisitedCards.value!![firstVisiblePosition])
                    }
                }
            },
            snapHelper = true
        )

        // Explore Setup
        setUpRecyclerView(
            binding.explore.exploreList,
            LinearLayoutManager.HORIZONTAL,
            exploreAdapter,
            MarginDecorator(context, right = true),
            snapHelper = true
        )
    }

    companion object {
        const val debounceDuration: Long = 500
    }
}
