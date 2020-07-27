package com.halcyonmobile.rsrvd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.halcyonmobile.rsrvd.databinding.ActivityMainBinding
import com.halcyonmobile.rsrvd.explore.ExploreFragment
import com.halcyonmobile.rsrvd.profile.ProfileFragment
import com.halcyonmobile.rsrvd.reservation.ReservationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
             when(menuItem.itemId){
                R.id.navigation_profile -> openFragment(ProfileFragment())
                R.id.navigation_reservations -> openFragment(ReservationFragment())
                R.id.navigation_explore -> openFragment(ExploreFragment())
                else -> false
            }
        }

        openFragment(ExploreFragment())
    }

    private fun openFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
        return true
    }
}