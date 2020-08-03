package com.halcyonmobile.rsrvd.explorevenues

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentExploreBinding
import com.halcyonmobile.rsrvd.utils.showSnackbar

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val searchResultsAdapter = CardsAdapter {
        // TODO start activity to open Details
    }
    private val recentlyViewedAdapter = CardsAdapter {
        // TODO start activity to open Details
    }
    private val exploreAdapter = CardsAdapter {
        // TODO start activity to open Details
    }

    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)
        viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

        // Observers
        viewModel.apply {
            recentlyVisitedCards.observe(viewLifecycleOwner) {
                recentlyViewedAdapter.submitList(it)
            }

            exploreCards.observe(viewLifecycleOwner) {
                exploreAdapter.submitList(it)
            }

            error.observe(viewLifecycleOwner) {
                if (it) {
                    view.showSnackbar("Something went wrong")
                }
            }

            cardInFocus.observe(viewLifecycleOwner) {
                binding.detailsDistance.text = viewModel.getFormattedDistance()
                binding.detailsActivity.visibility = if (cardInFocus.value?.location != null) View.VISIBLE else View.GONE
            }

            searchResults.observe(viewLifecycleOwner) {
                searchResultsAdapter.submitList(it)

                if (it.isNotEmpty()) {
                    binding.noResults.visibility = View.GONE
                    binding.searchResults.visibility = View.VISIBLE
                }
            }

            searching.observe(viewLifecycleOwner) {
                val isEmpty = viewModel.searchResults.value.isNullOrEmpty()

                binding.content.visibility = if (it) View.GONE else View.VISIBLE
                binding.noResults.visibility = if (it && isEmpty) View.VISIBLE else View.GONE
                binding.searchResults.visibility = if (it && !isEmpty) View.VISIBLE else View.GONE
                binding.searchVenueBar.clear.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        // LISTS

        // Search Results Setup
        binding.searchResultsList.apply {
            setHasFixedSize(false)
            adapter = searchResultsAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            addItemDecoration(MarginDecorator(bottom = true))
        }

        // Recently Visited Setup
        binding.recentlyVisitedList.apply {
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
        binding.exploreList.apply {
            setHasFixedSize(false)
            adapter = exploreAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            LinearSnapHelper().attachToRecyclerView(this)
            addItemDecoration(MarginDecorator(right = true))
        }

        // BUTTONS

        // Clear button
        binding.searchVenueBar.clear.setOnClickListener {
            binding.searchVenueBar.searchText.text.clear()
        }

        // Readme Button
        binding.readMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.read_more_link))))
        }

        // OTHER

        // Search term changed
        binding.searchVenueBar.searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchTermChanged(s.toString())
            }
        })
    }
}
