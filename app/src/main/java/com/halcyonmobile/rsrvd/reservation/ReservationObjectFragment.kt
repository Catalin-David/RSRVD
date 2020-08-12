package com.halcyonmobile.rsrvd.reservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentCollectionObjectBinding
import com.halcyonmobile.rsrvd.reservationdetails.ReservationDetailsActivity

class ReservationObjectFragment : Fragment(R.layout.fragment_collection_object) {
    private lateinit var binding: FragmentCollectionObjectBinding
    private val viewModel: ReservationObjectFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recyclerViewAdapter = ReservationAdapter { reservationId ->
            startActivityForResult(ReservationDetailsActivity.getStartIntent(requireContext(), reservationId), DETAILS_ACTIVITY_REQUEST_CODE)
        }

        binding = FragmentCollectionObjectBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.loadReservations()

        arguments?.takeIf { args -> args.containsKey(RESERVATION_TAB_INDEX_KEY) }?.apply {
            binding.recyclerView.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(context)
            }

            when (getInt(RESERVATION_TAB_INDEX_KEY)) {
                HISTORY_RESERVATIONS_TAB -> {
                    viewModel.historyReservations.observe(viewLifecycleOwner) { recyclerViewAdapter.submitList(it) }
                }
                UPCOMING_RESERVATIONS_TAB -> {
                    viewModel.upcomingReservations.observe(viewLifecycleOwner) { recyclerViewAdapter.submitList(it) }
                }
                else -> recyclerViewAdapter.submitList(emptyList())
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DETAILS_ACTIVITY_REQUEST_CODE -> {
                if (resultCode == LIST_HAS_CHANGED) {
                    viewModel.loadReservations()
                    Log.d("RESERVATION_OBJ_FRAG", "list has changed")
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val RESERVATION_TAB_INDEX_KEY = "com.halcyonmobile.rsrvd.reservation.RESERVATION_TAB_INDEX_KEY"
        private const val UPCOMING_RESERVATIONS_TAB = 0
        private const val HISTORY_RESERVATIONS_TAB = 1

        const val DETAILS_ACTIVITY_REQUEST_CODE = 1000
        const val LIST_HAS_CHANGED = 1001
        const val NO_CHANGES = 1002

        fun createInstance(index: Int) = ReservationObjectFragment().apply {
            arguments = Bundle().apply {
                putInt(RESERVATION_TAB_INDEX_KEY, index)
            }
        }
    }
}