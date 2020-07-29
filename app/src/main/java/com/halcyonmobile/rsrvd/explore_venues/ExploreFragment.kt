package com.halcyonmobile.rsrvd.explore_venues

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.CardsBinding
import com.halcyonmobile.rsrvd.databinding.SelectLocationBinding

class ExploreFragment: Fragment(R.layout.fragment_explore) {
    private lateinit var binding: CardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CardsBinding.inflate(layoutInflater)
    }
}