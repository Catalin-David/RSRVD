package com.halcyonmobile.rsrvd.feature.selectlocation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.databinding.PlaceSuggestionBinding

class PlaceViewHolder(private val view: View, private val listener: (Location) -> Unit) : RecyclerView.ViewHolder(view) {
    fun setLocation(location: Location) {
        PlaceSuggestionBinding.bind(view).location = location
        view.setOnClickListener { listener(location) }
    }
}
