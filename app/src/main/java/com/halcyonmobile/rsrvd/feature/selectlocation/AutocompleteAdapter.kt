package com.halcyonmobile.rsrvd.feature.selectlocation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.halcyonmobile.rsrvd.R

class AutocompleteAdapter : ListAdapter<Location, PlaceViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
        PlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.place_suggestion, parent, false))

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.setLocation(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Location> = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean = oldItem == newItem
        }
    }
}
