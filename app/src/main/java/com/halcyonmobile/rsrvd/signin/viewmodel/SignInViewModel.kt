package com.halcyonmobile.rsrvd.signin.viewmodel

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.R

class SignInViewModel(context: Context) : ViewModel() {

    var welcomeToTextView = ObservableField<String>()

    init {
        val ss = SpannableString(context.resources.getString(R.string.welcome_to_rsrvd))
        ss.setSpan(
            ForegroundColorSpan(context.resources.getColor(R.color.primary)),
            11,
            16,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        welcomeToTextView.set(ss.substring(0, ss.length))
    }

    fun justExplore () {}

    fun googleConnect() {

    }

}