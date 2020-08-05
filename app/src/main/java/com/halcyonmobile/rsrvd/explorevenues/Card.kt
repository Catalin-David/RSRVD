package com.halcyonmobile.rsrvd.explorevenues

import com.halcyonmobile.rsrvd.core.shared.Location
import java.util.*

data class Card(
    val id: UUID = UUID.randomUUID(),
    val idVenue: String,
    val title: String,
    val image: String? = null,
    val location: Location? = null
)

object StaticCards {
    val noRecents = Card(title = "No activity yet. But it looks like it’s time for some!", idVenue = "")

    val noExplore = Card(title = "No venues found", idVenue = "")
}
