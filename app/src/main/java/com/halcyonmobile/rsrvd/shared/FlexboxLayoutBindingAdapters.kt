package com.halcyonmobile.rsrvd.shared

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.onboarding.Interests

@BindingAdapter("inflateData")
fun inflateData(layout: FlexboxLayout, data: List<Interests>) {
    data.map { layout.addView(InterestView(layout.context).apply { setInterest(it.name) }) }
}

@BindingAdapter("visible")
fun visible(view: View, visibility: Boolean) {
    view.isVisible = visibility
}