package com.halcyonmobile.rsrvd.venuedetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.halcyonmobile.rsrvd.R

class ReviewInnerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_inner_venue_reviews, container, false)

    companion object {
        fun newInstance() : ReviewInnerFragment = ReviewInnerFragment()
    }
}
