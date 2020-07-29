package com.halcyonmobile.rsrvd.explore_venues.components.werecommend

import android.content.Context
import android.util.AttributeSet
import com.halcyonmobile.rsrvd.explore_venues.shared.card.Card
import com.halcyonmobile.rsrvd.explore_venues.shared.cards.CardsView

class WeRecommendView(context: Context, attributeSet: AttributeSet?) : CardsView(context, attributeSet) {
    init {
        adapter.submitList(
            listOf(
                Card(title = "g"),
                Card(title = "h"),
                Card(title = "i")
            )
        )
    }
}
