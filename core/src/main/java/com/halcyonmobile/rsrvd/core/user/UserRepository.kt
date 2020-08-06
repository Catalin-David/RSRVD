package com.halcyonmobile.rsrvd.core.user

import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.user.dto.UserDto

object UserRepository {
    private val userRemoteSource = UserRemoteSource()

    fun update(location: Location, interests: List<Interests>, callback: (Boolean) -> Unit) {
        userRemoteSource.update(location, interests, callback)
    }

    fun get(updateState: (UserDto?) -> Unit) {
        userRemoteSource.get(updateState)
    }
}