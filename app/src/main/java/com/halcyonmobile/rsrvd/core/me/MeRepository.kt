package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.onboarding.Interests
import com.halcyonmobile.rsrvd.selectlocation.Location

class MeRepository {
    private val meRemoteSource: MeRemoteSource = MeRemoteSource()

    fun update(location: Location, interests: List<Interests>, callback: (Boolean) -> Unit) {
        meRemoteSource.update(location, interests, callback)
    }
}