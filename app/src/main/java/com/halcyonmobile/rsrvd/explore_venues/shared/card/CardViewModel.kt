package com.halcyonmobile.rsrvd.explore_venues.shared.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardViewModel : ViewModel() {
    private val title: MutableLiveData<String> = MutableLiveData()

    fun getTitle(): LiveData<String> = title

    fun setTitle(newTitle: String) {
        title.value = newTitle
    }
}