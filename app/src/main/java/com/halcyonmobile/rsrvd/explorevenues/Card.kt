package com.halcyonmobile.rsrvd.explorevenues

import com.halcyonmobile.rsrvd.core.model.Location
import java.util.*

data class Card(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val image: String? = null,
    val location: Location? = null
)

object NoRecentCard {
    val instance = Card(title = "No activity yet. But it looks like it’s time for some!")
}
