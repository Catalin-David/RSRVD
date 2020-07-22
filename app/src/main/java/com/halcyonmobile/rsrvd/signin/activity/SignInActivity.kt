package com.halcyonmobile.rsrvd.signin.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivitySignInBinding
import com.halcyonmobile.rsrvd.signin.viewmodel.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        signInBinding.singInViewModel = SignInViewModel(this)
/*
        signInBinding.singInViewModel?.welcomeToTextView?.set(
            SpannableStringBuilder("welcome to \nrsrvd")
                .setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.primary)),
                    12,
                    16,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                ).toString())
        signInBinding.executePendingBindings()*/
    }
}