package com.halcyonmobile.rsrvd.explore_venues

import androidx.databinding.BindingAdapter

class CardsBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("loadRecentlyVisited")
        fun loadRecentlyVisited(layout: CardsView, data: List<Card>) {
            data.map { layout.addView(CardView(layout.context).apply { setSomething("") }) }
        }

        @JvmStatic
        @BindingAdapter("loadExplore")
        fun loadExplore(layout: CardsView, data: List<Card>) {
            data.map { layout.addView(CardView(layout.context).apply { setSomething("") }) }
        }

        @JvmStatic
        @BindingAdapter("loadWeRecommend")
        fun loadWeRecommend(layout: CardsView, data: List<Card>) {
            data.map { layout.addView(CardView(layout.context).apply { setSomething("") }) }
        }
    }
}
