package com.halcyonmobile.rsrvd

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    fun onNavigationItemSelected(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.navigation_profile -> {
                Log.d("TAG", "Profile fragment")
               true
            }
            R.id.navigation_explore -> {
                Log.d("TAG", "Explore fragment")
                true
            }
            R.id.navigation_reservations -> {
                Log.d("TAG", "Reservations fragment")
                true
            }
            else -> false
        }
    }
}