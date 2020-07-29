package com.halcyonmobile.rsrvd.explore_venues.shared.card

import java.util.*

data class Card(
    val id: UUID = UUID.randomUUID(),
    val title: String
)