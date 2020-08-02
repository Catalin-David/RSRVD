package com.halcyonmobile.rsrvd.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile){
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

        viewModel.userName.observe(viewLifecycleOwner, Observer<String>{
            binding.textViewProfileName.text = it
        })
        viewModel.imageUrl.observe(viewLifecycleOwner, Observer<String>{
            Glide.with(this).asBitmap().load(it).error(R.mipmap.ic_launcher).into(binding.imageViewProfilePicture)
        } )
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_array,
            R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerReminder.adapter = adapter
        }

        return binding.root
    }

    companion object{
        private var fragmentContext: Context? = null
        fun getLastSignedInAccount() = GoogleSignIn.getLastSignedInAccount(fragmentContext)
    }
}