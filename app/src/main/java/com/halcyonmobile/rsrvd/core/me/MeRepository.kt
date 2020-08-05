package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.me.dto.UserDto
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location


class MeRepository {
    private val meRemoteSource = MeRemoteSource()

    fun update(location: Location, interests: List<Interests>, callback: (Boolean) -> Unit) {
        meRemoteSource.update(location, interests, callback)
    }

    fun get(callback: (UserDto?) -> Unit) {
        meRemoteSource.get(callback)
    }
}