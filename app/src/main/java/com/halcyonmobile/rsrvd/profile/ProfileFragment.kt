package com.halcyonmobile.rsrvd.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.databinding.FragmentProfileBinding
import com.halcyonmobile.rsrvd.editprofile.EditProfileActivity
import com.halcyonmobile.rsrvd.signin.SignInActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

        viewModel.setSignInAccount(GoogleSignIn.getLastSignedInAccount(context))

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_array,
            R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerReminder.adapter = adapter
        }

        binding.buttonLogOut.setOnClickListener {
            AlertDialog.Builder(binding.root.context).apply {
                setTitle(getString(R.string.log_out_prompt))
                setPositiveButton(getString(R.string.log_out)){_, _ ->
                    GoogleSignIn.getClient(
                        context,
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                    )
                        .signOut()
                        .addOnCompleteListener {
                            viewModel.isUserLoggedIn = false
                            viewModel.exploreFirst = false
                            viewModel.accessToken = ""
                            viewModel.repoLocation = Pair(0.0, 0.0)
                            startActivity(Intent(activity, SignInActivity::class.java))
                        }
                }
                setNegativeButton(getString(R.string.cancel)){_, _ -> }
                show()
            }
        }

        binding.buttonEditProfile.setOnClickListener {
            if(viewModel.isUserLoggedIn){
                startActivity(Intent(this.activity, EditProfileActivity::class.java))
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadUserInformation()
    }
}