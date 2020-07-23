package com.halcyonmobile.rsrvd.feature.onboarding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.feature.selectlocation.Location

class OnboardingViewModel : ViewModel() {
    private val location: MutableLiveData<Location> = MutableLiveData()

    fun getLocation(): LiveData<Location> = location

    fun setLocation(newLocation: MutableLiveData<Location>) {
        location.value = newLocation.value
    }

    companion object {
        @JvmStatic
        @BindingAdapter("inflateData")
        fun inflateData(layout: FlexboxLayout, data: List<Interests>) {
            for (entry in data) {
                layout.addView(InterestView(layout.context).apply { setInterest(entry.name) })
            }
        }
    }
}