package com.halcyonmobile.rsrvd.reservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ReservationFragmentAdapter(fragment: Fragment, private val tabList: List<String>): FragmentStateAdapter(fragment) {
    override fun getItemCount() = tabList.size

    override fun createFragment(position: Int): Fragment = ReservationObjectFragment().apply {
        arguments = Bundle().apply {
            putInt(RESERVATION_TAB_INDEX_KEY, position)
        }
    }

    companion object{
        const val UPCOMING_RESERVATIONS_TAB = 0
        const val HISTORY_RESERVATIONS_TAB = 1
        const val RESERVATION_TAB_INDEX_KEY = "com.halcyonmobile.rsrvd.reservation.RESERVATION_TAB_INDEX_KEY"
    }
}