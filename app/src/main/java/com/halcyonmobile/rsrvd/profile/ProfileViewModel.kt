package com.halcyonmobile.rsrvd.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.halcyonmobile.rsrvd.core.dto.UserResponseDto
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.onboarding.Interests

class ProfileViewModel : ViewModel() {
    private val account: MutableLiveData<GoogleSignInAccount?> = MutableLiveData()
    private val profileData: MutableLiveData<UserProfileData> = MutableLiveData()

    init {
        loadAccount()
        profileData.value =
            UserProfileData("CLUJ-NAPOCA, RO", 16, listOf(Interests.FOOTBALL, Interests.BASKETBALL))
        //loadUserInformation()
    }

    private fun loadAccount() {
        account.value = ProfileFragment.getLastSignedInAccount()
    }

    private fun loadUserInformation() {
        val userInformation: UserResponseDto? = UserRepository.getUserProfileData()
        userInformation?.let {
            profileData.value = UserProfileData(userInformation._location.toString(), userInformation._reservations, userInformation._interests.map {
                when (it) {
                    "RUNNING" -> Interests.RUNNING
                    "WORKOUT" -> Interests.WORKOUT
                    "YOGA" -> Interests.YOGA
                    "BASKETBALL" -> Interests.BASKETBALL
                    "TENNIS" -> Interests.TENNIS
                    "VOLLEY" -> Interests.VOLLEY
                    "BADMINTON" -> Interests.BADMINTON
                    "HANDBALL" -> Interests.HANDBALL
                    else -> Interests.FOOTBALL
                }
            })
        }
    }

    val userName: LiveData<String> = Transformations.map(account) { it?.displayName }
    val imageUrl: LiveData<String> = Transformations.map(account) { it?.photoUrl.toString() }
    val location: LiveData<String> = Transformations.map(profileData) { it.location }
    val activities: LiveData<String> = Transformations.map(profileData) { it.activitiesCompleted.toString() }
    val interests: LiveData<List<Interests>> = Transformations.map(profileData) { it.interests }
}