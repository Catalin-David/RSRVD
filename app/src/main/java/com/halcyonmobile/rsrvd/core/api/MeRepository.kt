package com.halcyonmobile.rsrvd.core.api

import com.halcyonmobile.rsrvd.feature.onboarding.Interests
import com.halcyonmobile.rsrvd.feature.selectlocation.Location

class MeRepository {
    private val meRemoteSource: MeRemoteSource = MeRemoteSource()

    fun update(location: Location, interests: List<Interests>, callback: (Boolean) -> Unit) {
        meRemoteSource.update(location, interests, callback)
    }
}