package com.halcyonmobile.rsrvd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.halcyonmobile.rsrvd.databinding.ActivityMainBinding
import com.halcyonmobile.rsrvd.explore.ExploreFragment
import com.halcyonmobile.rsrvd.profile.signup.SignUpActivity
import com.halcyonmobile.rsrvd.reservation.ReservationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_profile -> {
                    startActivity(Intent(this, SignUpActivity::class.java))
                  //  openFragment(ProfileFragment(), supportFragmentManager)
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
    }

    private fun openFragment(fragment: Fragment, fragmentManager: FragmentManager, addToBackStackParameter: String? = null){
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(addToBackStackParameter)
            commit()
        }
    }

}