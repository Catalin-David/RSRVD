package com.halcyonmobile.rsrvd.explorevenues

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentExploreBinding
import com.halcyonmobile.rsrvd.explorevenues.filter.FilterActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val recentlyViewedAdapter = CardsAdapter {
        // TODO start activity to open Details
    }
    private val exploreAdapter = CardsAdapter {
        // TODO start activity to open Details
    }

    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentExploreBinding.bind(view)
        viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

        binding.header.viewModel = viewModel
        binding.header.lifecycleOwner = this

        // Observers
        viewModel.apply {
            recentlyVisitedCards.observe(viewLifecycleOwner) {
                recentlyViewedAdapter.submitList(it)
                setCardInFocus(recentlyVisitedCards.value?.get(0))
            }
            exploreCards.observe(viewLifecycleOwner) { exploreAdapter.submitList(it) }
            error.observe(viewLifecycleOwner) { if (it) view.showSnackbar("Something went wrong").show() }
            cardInFocus.observe(viewLifecycleOwner) { binding.detailsDistance.text = viewModel.getFormattedDistance() }
            filters.observe(viewLifecycleOwner) { viewModel.filterVenues() }
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
                    viewModel.setCardInFocus(viewModel.recentlyVisitedCards.value?.get(firstVisiblePosition))
                }
            }
        }

        // Explore Setup
        binding.exploreList.apply {
            setHasFixedSize(false)
            adapter = exploreAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            LinearSnapHelper().attachToRecyclerView(this)
        }

        // Readme Button
        binding.readMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.read_more_link))))
        }

        // Filter button
        binding.searchVenueBar.filterIcon.setOnClickListener {
            startActivityForResult(Intent(activity, FilterActivity::class.java), FILTER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && resultCode == Activity.RESULT_OK && requestCode == FILTER_REQUEST_CODE) {
            viewModel.setFilters(data.getParcelableExtra("filters"))
        }
    }

    companion object {
        const val FILTER_REQUEST_CODE = 1
    }
}
