package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.onboarding.Interests
import com.halcyonmobile.rsrvd.selectlocation.Location


object MeRepository {
    private val meRemoteSource = MeRemoteSource()

    fun update(location: Location, interests: List<Interests>, callback: (Boolean) -> Unit) =
        meRemoteSource.update(location, interests, callback)
}