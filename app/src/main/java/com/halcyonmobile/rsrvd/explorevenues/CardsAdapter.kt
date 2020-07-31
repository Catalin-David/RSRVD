package com.halcyonmobile.rsrvd.explorevenues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
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
        }
    }
}
