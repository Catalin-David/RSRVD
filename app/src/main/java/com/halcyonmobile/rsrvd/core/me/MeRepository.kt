package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.model.Interests
import com.halcyonmobile.rsrvd.core.model.Location


class MeRepository {
    private val meRemoteSource = MeRemoteSource()

    fun update(location: Location, interests: List<Interests>, callback: (Boolean) -> Unit) {
        meRemoteSource.update(location, interests, callback)
    }
}