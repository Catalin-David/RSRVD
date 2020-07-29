package com.halcyonmobile.rsrvd.feature.shared

import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.feature.onboarding.InterestView
import com.halcyonmobile.rsrvd.feature.onboarding.Interests

class FlexboxLayoutBindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("inflateData")
        fun inflateData(layout: FlexboxLayout, data: List<Interests>) {
            data.map { layout.addView(InterestView(layout.context).apply { setInterest(it.name) }) }
        }
    }
}