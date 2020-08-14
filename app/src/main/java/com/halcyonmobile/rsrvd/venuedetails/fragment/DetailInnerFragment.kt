package com.halcyonmobile.rsrvd.venuedetails.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentInnerVenueDetailBinding
import com.halcyonmobile.rsrvd.venuedetails.VenueDetailViewModel

class DetailInnerFragment : Fragment() {
    private lateinit var binding: FragmentInnerVenueDetailBinding
    private lateinit var viewModel: VenueDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInnerVenueDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        arguments?.getString(EXTRA_VENUE_ID)?.let { viewModel.getVenue(it) }

        binding.apply {
            youtubeVideoContainer.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.venue_tour_link))))
            }

            containerDirections.setOnClickListener {
                viewModel?.venue?.value?.location?.let {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.google_maps, it.latitude, it.longitude))))
                }
            }
        }
    }

    companion object {
        private const val EXTRA_VENUE_ID = "EXTRA_VENUE_ID"

        fun newInstance(venueId: String): DetailInnerFragment =
            DetailInnerFragment().apply {
                arguments = Bundle().apply { putString(EXTRA_VENUE_ID, venueId) }
            }
    }
}
