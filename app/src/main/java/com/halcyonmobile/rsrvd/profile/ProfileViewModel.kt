package com.halcyonmobile.rsrvd.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.core.user.UserProfileData
import com.halcyonmobile.rsrvd.core.user.UserRepository

class ProfileViewModel : ViewModel() {
    private val account: MutableLiveData<GoogleSignInAccount?> = MutableLiveData(null)
    private val profileData: MutableLiveData<UserProfileData> = MutableLiveData(UserProfileData(null))

    val userName: LiveData<String> = Transformations.map(account) { it?.displayName }
    val imageUrl: LiveData<Uri> = Transformations.map(account) { it?.photoUrl }
    val location: LiveData<String> = Transformations.map(profileData) { it.location?.name ?: "" }
    val activities: LiveData<String> = Transformations.map(profileData) { it.activitiesCompleted.toString() }
    val interests: LiveData<List<Interests>> = Transformations.map(profileData) { it.interests }

    fun setSignInAccount(newSignInAccount: GoogleSignInAccount?) {
        account.value = newSignInAccount
    }

    fun loadUserInformation() {
        UserRepository.get {
            it?.let {
                profileData.value = UserProfileData(it.location, it.reservations, it.interests)
            }
        }
    }

    fun handleLogOut() {
        UserLocalRepository.apply {
            isUserLoggedIn = false
            exploreFirst = false
            accessToken = ""
            location = Pair(0.0, 0.0)
        }
    }

    fun isUserLoggedIn() = UserLocalRepository.isUserLoggedIn
}