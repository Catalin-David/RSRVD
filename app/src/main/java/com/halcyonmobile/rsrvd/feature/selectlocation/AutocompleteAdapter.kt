package com.halcyonmobile.rsrvd.feature.selectlocation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.halcyonmobile.rsrvd.databinding.PlaceSuggestionBinding

class AutocompleteAdapter(private val listener: (Location) -> Unit) : ListAdapter<Location, PlaceViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
        PlaceViewHolder(PlaceSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.setLocation(getItem(position))
        holder.itemView.setOnClickListener  { listener(getItem(position)) }
    }

    override fun getItemCount(): Int = currentList.size

    companion object {
        private const val TAG = "AutocompleteAdapter"

        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Location> = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean = oldItem == newItem
        }
    }
}
