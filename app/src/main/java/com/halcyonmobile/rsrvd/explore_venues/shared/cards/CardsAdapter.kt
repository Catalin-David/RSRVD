package com.halcyonmobile.rsrvd.explore_venues.shared.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.halcyonmobile.rsrvd.databinding.CardBinding
import com.halcyonmobile.rsrvd.explore_venues.shared.card.CardViewHolder
import com.halcyonmobile.rsrvd.explore_venues.shared.card.Card

class CardsAdapter(private val listener: (Card) -> Unit) : ListAdapter<Card, CardViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            CardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.setTitle(getItem(position).title)
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

    override fun getItemCount(): Int = currentList.size

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Card> = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem == newItem
        }
    }
}
