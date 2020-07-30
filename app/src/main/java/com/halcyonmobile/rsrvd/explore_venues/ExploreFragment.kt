package com.halcyonmobile.rsrvd.explore_venues

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentExploreBinding

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val recentlyViewedAdapter = CardsAdapter {
        // TODO start activity to open Details
    }
    private val exploreAdapter = CardsAdapter {
        // TODO start activity to open Details
    }

    private val viewModel = ExploreViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding: FragmentExploreBinding = FragmentExploreBinding.bind(view)

        viewModel.recentlyVisitedCards.observe(viewLifecycleOwner) { recentlyViewedAdapter.submitList(it) }

        viewModel.exploreCards.observe(viewLifecycleOwner) { exploreAdapter.submitList(it) }

        // Recently Visited Setup
        binding.recentlyVisitedList.apply {
            setHasFixedSize(true)
            adapter = recentlyViewedAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            LinearSnapHelper().attachToRecyclerView(this)
        }

        // Explore Setup
        binding.exploreList.apply {
            setHasFixedSize(true)
            adapter = exploreAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            LinearSnapHelper().attachToRecyclerView(this)
        }

        // Readme Button
        binding.readMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.read_more_link))))
        }

        // TODO retrieve cards
        viewModel.setRecentlyVisitedCards(listOf(Card(title="a"), Card(title="c")))

        // TODO retrieve cards
        viewModel.setExploreCards(listOf(Card(title="b"), Card(title="a")))
    }
}