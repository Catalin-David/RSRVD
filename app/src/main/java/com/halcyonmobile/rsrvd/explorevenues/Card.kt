package com.halcyonmobile.rsrvd.explorevenues

import java.util.*

data class Card(
    val id: UUID = UUID.randomUUID(),
    val title: String
)

object NoRecentCard {
    val instance = Card(title = "No activity yet. But it looks like it’s time for some!")
}