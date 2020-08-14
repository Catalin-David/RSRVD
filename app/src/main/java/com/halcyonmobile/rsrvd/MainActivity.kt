package com.halcyonmobile.rsrvd

import android.content.Context
import android.content.Intent
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
import com.halcyonmobile.rsrvd.shared.FragmentDecision
import com.halcyonmobile.rsrvd.signin.SignInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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


        when (intent.getSerializableExtra(EXTRA_MESSAGE)) {
            FragmentDecision.RESERVATION -> {
                openFragment(ReservationFragment(), supportFragmentManager)
                binding.bottomNavigationView.selectedItemId = R.id.navigation_reservations
            }
            FragmentDecision.PROFILE -> {
                openFragment(ProfileFragment(), supportFragmentManager)
                binding.bottomNavigationView.selectedItemId = R.id.navigation_profile
            }
            else -> {
                openFragment(ExploreFragment(), supportFragmentManager)
                binding.bottomNavigationView.selectedItemId = R.id.navigation_explore
            }
        }
    }

    private fun openFragment(fragment: Fragment, fragmentManager: FragmentManager, addToBackStackParameter: String? = null) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(addToBackStackParameter)
            commit()
        }
    }

    companion object {
        private const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        private const val MESSAGE = "message"

        fun instanceAfterReservation(context: Context, fragment: FragmentDecision, message: String? = null): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(EXTRA_MESSAGE, fragment)
            intent.putExtra(MESSAGE, message)

            return intent
        }
    }

}