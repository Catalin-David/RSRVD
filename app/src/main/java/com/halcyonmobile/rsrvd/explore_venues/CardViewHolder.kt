package com.halcyonmobile.rsrvd.explore_venues

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.CardBinding

class CardViewHolder(private val binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setTitle(title: String) {
        binding.titleTextView.text = title
        binding.titleTextView.setShadowLayer(1f,1f,1f, Color.BLACK);
        binding.executePendingBindings()
    }

    fun setImage(source: String) {
        Glide
            .with(binding.root)
            .load(source)
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .into(binding.background)
    }
    
}
