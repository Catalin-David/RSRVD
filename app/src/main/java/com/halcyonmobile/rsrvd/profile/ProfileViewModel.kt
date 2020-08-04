package com.halcyonmobile.rsrvd.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.core.model.UserProfileData
import com.halcyonmobile.rsrvd.core.model.Interests

class ProfileViewModel : ViewModel() {
    private var signInAccount: GoogleSignInAccount? = null
    private val account: MutableLiveData<GoogleSignInAccount?> = MutableLiveData()
    private val profileData: MutableLiveData<UserProfileData> = MutableLiveData()

    init {
        loadAccount()
        initializeDefaults()
        //profileData.value =
        //UserProfileData("CLUJ-NAPOCA, RO", 16, listOf(Interests.FOOTBALL, Interests.BASKETBALL))
        loadUserInformation()
    }

    private fun initializeDefaults() {
        profileData.value = UserProfileData()
    }

    fun setSignInAccount(newSignInAccount: GoogleSignInAccount?) {
        signInAccount = newSignInAccount
        loadAccount()
    }

    private fun loadAccount() {
        account.value = signInAccount
    }

    private fun loadUserInformation() {
        UserRepository.loadProfileData(
            onSuccess = {
                profileData.value = it
            })
    }

    val userName: LiveData<String> = Transformations.map(account) { it?.displayName }
    val imageUrl: LiveData<Uri> = Transformations.map(account) { it?.photoUrl }
    val location: LiveData<String> = Transformations.map(profileData) { it.location?.name ?: "" }
    val activities: LiveData<String> = Transformations.map(profileData) { it.activitiesCompleted.toString() }
    val interests: LiveData<List<Interests>> = Transformations.map(profileData) { it.interests }
}