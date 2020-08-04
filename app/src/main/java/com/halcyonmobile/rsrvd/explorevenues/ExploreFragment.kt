package com.halcyonmobile.rsrvd.explorevenues

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentExploreBinding
import com.halcyonmobile.rsrvd.utils.debounce
import com.halcyonmobile.rsrvd.utils.showSnackbar

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel

    private val searchResultsAdapter = CardsAdapter { /* TODO start activity to open Details */ }
    private val recentlyViewedAdapter = CardsAdapter { /* TODO start activity to open Details */ }
    private val exploreAdapter = CardsAdapter { /* TODO start activity to open Details */ }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)
        viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

        setUpObservers()
        setUpLists()

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = activity

        binding.activityInfo.viewModel = this.viewModel
        binding.activityInfo.lifecycleOwner = activity

        binding.searchResults.viewModel = this.viewModel
        binding.searchResults.lifecycleOwner = activity

        binding.noResults.viewModel = this.viewModel
        binding.noResults.lifecycleOwner = activity

        binding.searchVenueBar.viewModel = this.viewModel
        binding.searchVenueBar.lifecycleOwner = activity

        binding.readMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.read_more_link))))
        }
    }

    private fun setUpObservers() {
        viewModel.apply {
            searchResults.observe(viewLifecycleOwner) { searchResultsAdapter.submitList(it) }
            recentlyVisitedCards.observe(viewLifecycleOwner) { recentlyViewedAdapter.submitList(it) }
            exploreCards.observe(viewLifecycleOwner) { exploreAdapter.submitList(it) }

            error.observe(viewLifecycleOwner) { if (it) view?.showSnackbar(getString(R.string.something_went_wrong)) }

            searchTerm.observe(viewLifecycleOwner) {
                debounce(300L, viewLifecycleOwner.lifecycleScope, viewModel::searchTermChanged)(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpLists() {
        // Search Results Setup
        binding.searchResults.searchResultsList.apply {
            setHasFixedSize(false)
            adapter = searchResultsAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            addItemDecoration(MarginDecorator(bottom = true))
        }

        // Recently Visited Setup
        binding.recentlyVisited.recentlyVisitedList.apply {
            setHasFixedSize(false)
            adapter = recentlyViewedAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            LinearSnapHelper().attachToRecyclerView(this)
            setOnScrollChangeListener { _, _, _, _, _ ->
                viewModel.recentlyVisitedCards.value?.isNotEmpty().let {
                    val layoutManager = layoutManager as LinearLayoutManager
                    val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
                    viewModel.setCardInFocus(viewModel.recentlyVisitedCards.value!![firstVisiblePosition])
                }
            }
            addItemDecoration(MarginDecorator(right = true))
        }

        // Explore Setup
        binding.explore.exploreList.apply {
            setHasFixedSize(false)
            adapter = exploreAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            LinearSnapHelper().attachToRecyclerView(this)
            addItemDecoration(MarginDecorator(right = true))
        }
    }
}
