package com.halcyonmobile.rsrvd.explorevenues

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ItemCardBinding

class CardsAdapter(private val listener: (Card) -> Unit) : ListAdapter<Card, CardsAdapter.CardViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.apply {
            holder.bind(getItem(position))
            itemView.setOnClickListener { listener(getItem(position)) }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Card> = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem == newItem
        }
    }

    inner class CardViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Card) {
            binding.titleTextView.text = item.title
            binding.titleTextView.setShadowLayer(1f, 1f, 1f, Color.BLACK);

            item.image?.let {
                Glide
                    .with(binding.root)
                    .load(item.image)
                    .placeholder(R.drawable.ic_baseline_cloud_download_24)
                    .into(binding.background)
            }
        }
    }
}
