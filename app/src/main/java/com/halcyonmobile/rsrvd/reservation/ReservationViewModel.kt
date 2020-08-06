package com.halcyonmobile.rsrvd.reservation

import androidx.lifecycle.ViewModel

class ReservationViewModel: ViewModel() {
    private val tabList = mutableListOf<String>()

    fun getTabList(): List<String> = tabList
    fun setTabList(newTabList: List<String>) = tabList.apply {
        clear()
        addAll(newTabList)
    }
}