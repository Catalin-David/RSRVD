package com.halcyonmobile.rsrvd.explorevenues

import com.halcyonmobile.rsrvd.core.shared.Location
import java.util.UUID

data class Card(
    val id: UUID = UUID.randomUUID(),
    val idVenue: String,
    val title: String,
    val image: String? = null,
    val location: Location? = null
)