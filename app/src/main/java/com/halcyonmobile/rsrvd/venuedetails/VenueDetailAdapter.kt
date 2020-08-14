package com.halcyonmobile.rsrvd.venuedetails

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.halcyonmobile.rsrvd.venuedetails.fragment.DetailInnerFragment
import com.halcyonmobile.rsrvd.venuedetails.fragment.ReviewInnerFragment

class VenueDetailAdapter(
    activity: AppCompatActivity,
    private val category: List<String>,
    private val venueId: String
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = category.size

    override fun createFragment(position: Int): Fragment =
        if (position == 0) DetailInnerFragment.newInstance(venueId)
        else ReviewInnerFragment.newInstance()

}
