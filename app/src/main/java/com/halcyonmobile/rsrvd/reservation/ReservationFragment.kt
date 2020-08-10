package com.halcyonmobile.rsrvd.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentReservationBinding

class ReservationFragment : Fragment(R.layout.fragment_reservation) {
    private lateinit var binding: FragmentReservationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val tabList = listOf(getString(R.string.tabname_upcoming), getString(R.string.tabname_history))

        binding = FragmentReservationBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewPager.adapter = ReservationFragmentAdapter(this@ReservationFragment, tabList)
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabList[position]
        }.attach()

        return binding.root
    }
}
