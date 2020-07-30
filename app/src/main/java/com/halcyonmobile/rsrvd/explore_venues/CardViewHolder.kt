package com.halcyonmobile.rsrvd.explore_venues

import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.databinding.CardBinding

class CardViewHolder(private val binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setTitle(title: String) {
        binding.titleTextView.text = title
        binding.executePendingBindings()
    }
}
