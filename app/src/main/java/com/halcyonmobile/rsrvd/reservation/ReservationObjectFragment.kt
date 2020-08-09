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

class ReservationObjectFragment: Fragment(R.layout.fragment_collection_object){
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

        arguments?.takeIf { args -> args.containsKey(ReservationFragmentAdapter.RESERVATION_TAB_INDEX_KEY) }?.apply {
            binding.recyclerView.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(context)
            }

            when(getInt(ReservationFragmentAdapter.RESERVATION_TAB_INDEX_KEY)){
                ReservationFragmentAdapter.HISTORY_RESERVATIONS_TAB -> {
                    viewModel.historyReservations.observe(viewLifecycleOwner){recyclerViewAdapter.submitList(it)}
                }
                ReservationFragmentAdapter.UPCOMING_RESERVATIONS_TAB ->  {
                    viewModel.upcomingReservations.observe(viewLifecycleOwner){recyclerViewAdapter.submitList(it)}
                }
                else -> recyclerViewAdapter.submitList(listOf())
            }
        }

        return binding.root
    }
}