package com.halcyonmobile.rsrvd.explore_venues.shared.cards

import androidx.databinding.BindingAdapter
import com.halcyonmobile.rsrvd.explore_venues.shared.card.CardView
import com.halcyonmobile.rsrvd.explore_venues.shared.card.Card

class CardsBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("loadList")
        fun loadList(layout: CardsView, data: List<Card>) {
            data.map { layout.addView(CardView(layout.context).apply { viewModel.setTitle(it.title) }) }
        }
    }
}
