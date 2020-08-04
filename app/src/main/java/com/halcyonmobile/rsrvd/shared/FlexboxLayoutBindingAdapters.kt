package com.halcyonmobile.rsrvd.shared

import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.core.model.Interests
import com.halcyonmobile.rsrvd.onboarding.InterestView

@BindingAdapter("inflateData")
fun inflateData(layout: FlexboxLayout, data: List<Interests>) {
    data.map { layout.addView(InterestView(layout.context).apply { setInterest(it.name) }) }
}
