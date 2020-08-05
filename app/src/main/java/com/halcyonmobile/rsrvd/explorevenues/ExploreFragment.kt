package com.halcyonmobile.rsrvd.explorevenues

import android.annotation.SuppressLint
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
import com.halcyonmobile.rsrvd.utils.showSnackbar


class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val recentlyVisitedAdapter = CardsAdapter {
        // TODO start activity to open Details
    }
    private val exploreAdapter = CardsAdapter {
        // TODO start activity to open Details
    }

    private lateinit var binding: FragmentExploreBinding

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentExploreBinding.bind(view)

        val viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

        // Observers
        viewModel.apply {
            recentlyVisitedCards.observe(viewLifecycleOwner) {
                recentlyVisitedAdapter.submitList(it)
                viewModel.setCardInFocus(viewModel.recentlyVisitedCards.value?.get(0))
            }
            exploreCards.observe(viewLifecycleOwner) { exploreAdapter.submitList(it) }
            errorRecentlyVisited.observe(viewLifecycleOwner) { if (it) view.showSnackbar("Something went wrong") }
            errorExplore.observe(viewLifecycleOwner) { if (it) view.showSnackbar("Something went wrong") }
            cardInFocus.observe(viewLifecycleOwner) { binding.detailsDistance.text = viewModel.getFormattedDistance() }
            noRecents.observe(viewLifecycleOwner) { binding.detailsDistance.visibility = if (it) View.VISIBLE else View.INVISIBLE }
        }

        // Recently Visited Setup
        binding.recentlyVisitedList.apply {
            setHasFixedSize(false)
            adapter = recentlyVisitedAdapter
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
    }
}
