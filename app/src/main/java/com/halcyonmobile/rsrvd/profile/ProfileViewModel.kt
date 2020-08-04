package com.halcyonmobile.rsrvd.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.onboarding.Interests

class ProfileViewModel : ViewModel() {
    private val account: MutableLiveData<GoogleSignInAccount?> = MutableLiveData()
    private val profileData: MutableLiveData<UserProfileData> = MutableLiveData()

    init {
        loadAccount()
        //profileData.value =
            //UserProfileData("CLUJ-NAPOCA, RO", 16, listOf(Interests.FOOTBALL, Interests.BASKETBALL))
        loadUserInformation()
    }

    private fun loadAccount() {
        account.value = ProfileFragment.getLastSignedInAccount()
    }

    private fun loadUserInformation() {
        profileData.value = UserRepository.getUserProfileData()
    }

    val userName: LiveData<String> = Transformations.map(account) { it?.displayName }
    val imageUrl: LiveData<String> = Transformations.map(account) { it?.photoUrl.toString() }
    val location: LiveData<String> = Transformations.map(profileData) { it.location?.name }
    val activities: LiveData<String> = Transformations.map(profileData) { it.activitiesCompleted.toString() }
    val interests: LiveData<List<Interests>> = Transformations.map(profileData) { it.interests }
}