package com.halcyonmobile.rsrvd.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentCollectionObjectBinding

class ReservationObjectFragment : Fragment(R.layout.fragment_collection_object) {
    private lateinit var binding: FragmentCollectionObjectBinding
    private val viewModel: ReservationObjectFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recyclerViewAdapter = ReservationAdapter()

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

    companion object{
        private const val RESERVATION_TAB_INDEX_KEY = "com.halcyonmobile.rsrvd.reservation.RESERVATION_TAB_INDEX_KEY"
        private const val UPCOMING_RESERVATIONS_TAB = 0
        private const val HISTORY_RESERVATIONS_TAB = 1

        fun createInstance(index: Int) = ReservationObjectFragment().apply {
            arguments = Bundle().apply {
                putInt(RESERVATION_TAB_INDEX_KEY, index)
            }
        }
    }
}