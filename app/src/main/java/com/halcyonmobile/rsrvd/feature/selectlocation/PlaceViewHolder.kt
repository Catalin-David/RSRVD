package com.halcyonmobile.rsrvd.feature.selectlocation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.databinding.PlaceSuggestionBinding

class PlaceViewHolder(private val binding: PlaceSuggestionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setLocation(location: Location) {
        binding.location = location
        binding.executePendingBindings()
    }
}
