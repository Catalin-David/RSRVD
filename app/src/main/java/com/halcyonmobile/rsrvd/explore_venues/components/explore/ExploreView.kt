package com.halcyonmobile.rsrvd.explore_venues.components.explore

import android.content.Context
import android.util.AttributeSet
import com.halcyonmobile.rsrvd.explore_venues.shared.card.Card
import com.halcyonmobile.rsrvd.explore_venues.shared.cards.CardsView

class ExploreView(context: Context, attributeSet: AttributeSet?) : CardsView(context, attributeSet) {
    init {
        adapter.submitList(
            listOf(
                Card(title = "d"),
                Card(title = "e"),
                Card(title = "f")
            )
        )
    }
}