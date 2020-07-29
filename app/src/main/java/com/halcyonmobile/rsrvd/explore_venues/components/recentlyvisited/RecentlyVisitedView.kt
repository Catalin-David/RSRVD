package com.halcyonmobile.rsrvd.explore_venues.components.recentlyvisited

import android.content.Context
import android.util.AttributeSet
import com.halcyonmobile.rsrvd.explore_venues.shared.card.Card
import com.halcyonmobile.rsrvd.explore_venues.shared.cards.CardsView

class RecentlyVisitedView(context: Context, attributeSet: AttributeSet?) : CardsView(context, attributeSet) {
    init {
        adapter.submitList(
            listOf(
                Card(title = "a"),
                Card(title = "b"),
                Card(title = "c")
            )
        )
    }
}
