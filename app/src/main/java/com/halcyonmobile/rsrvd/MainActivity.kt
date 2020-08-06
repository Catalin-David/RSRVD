package com.halcyonmobile.rsrvd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.databinding.ActivityMainBinding
import com.halcyonmobile.rsrvd.explorevenues.ExploreFragment
import com.halcyonmobile.rsrvd.profile.ProfileFragment
import com.halcyonmobile.rsrvd.reservation.ReservationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_profile -> {
                    if (UserLocalRepository.exploreFirst && !UserLocalRepository.isUserLoggedIn) {
                        startActivity(SignInActivity.getStartIntent(this, true))
                    } else {
                        openFragment(ProfileFragment(), supportFragmentManager)
                    }
                    true
                }
                R.id.navigation_reservations -> {
                    openFragment(ReservationFragment(), supportFragmentManager)
                    true
                }
                R.id.navigation_explore -> {
                    openFragment(ExploreFragment(), supportFragmentManager)
                    true
                }
                else -> false
            }
        }

        openFragment(ExploreFragment(), supportFragmentManager)
    }

    private fun openFragment(fragment: Fragment, fragmentManager: FragmentManager, addToBackStackParameter: String? = null) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(addToBackStackParameter)
            commit()
        }
    }
}