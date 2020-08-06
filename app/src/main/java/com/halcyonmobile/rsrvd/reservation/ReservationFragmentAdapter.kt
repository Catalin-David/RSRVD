package com.halcyonmobile.rsrvd.reservation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ReservationFragmentAdapter(fragment: Fragment, private val tabList: List<String>): FragmentStateAdapter(fragment) {
    override fun getItemCount() = tabList.size

    override fun createFragment(position: Int): Fragment = ReservationObjectFragment()
}