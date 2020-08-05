package com.halcyonmobile.rsrvd.selectlocation

import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.core.model.Location
import com.halcyonmobile.rsrvd.databinding.PlaceSuggestionBinding

class PlaceViewHolder(private val binding: PlaceSuggestionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setLocation(location: Location) {
        binding.location = location
        binding.executePendingBindings()
    }
}
