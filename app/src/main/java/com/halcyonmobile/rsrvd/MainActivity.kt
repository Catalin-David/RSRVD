package com.halcyonmobile.rsrvd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.halcyonmobile.rsrvd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
             when(menuItem.itemId){
                R.id.navigation_profile -> {
                    // TODO: navigate to profile fragment
                    true
                }
                R.id.navigation_reservations -> {
                    // TODO: navigate to reservations fragment
                     true
                }
                R.id.navigation_explore -> {
                    // TODO: navigate to explore fragment
                     true
                }
                else -> false
            }
        }
    }
}