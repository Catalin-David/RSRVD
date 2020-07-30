package com.halcyonmobile.rsrvd.explore_venues

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentExploreBinding
import com.halcyonmobile.rsrvd.utils.showSnackbar

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val recentlyViewedAdapter = CardsAdapter {
        // TODO start activity to open Details
    }
    private val exploreAdapter = CardsAdapter {
        // TODO start activity to open Details
    }

    private lateinit var binding: FragmentExploreBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentExploreBinding.bind(view)

        // Observers
        ViewModelProviders.of(this).get(ExploreViewModel::class.java).apply {
            recentlyVisitedCards.observe(viewLifecycleOwner) { recentlyViewedAdapter.submitList(it) }
            exploreCards.observe(viewLifecycleOwner) { exploreAdapter.submitList(it) }
            error.observe(viewLifecycleOwner) { if (it) view.showSnackbar("Something went wrong") }
        }

        // Recently Visited Setup
        binding.recentlyVisitedList.apply {
            setHasFixedSize(false)
            adapter = recentlyViewedAdapter
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            LinearSnapHelper().attachToRecyclerView(this)
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
    }
}