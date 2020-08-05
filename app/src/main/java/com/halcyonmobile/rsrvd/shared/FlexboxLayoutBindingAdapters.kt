package com.halcyonmobile.rsrvd.shared

import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.core.model.Interests
import com.halcyonmobile.rsrvd.onboarding.InterestView

@BindingAdapter("inflateData")
fun FlexboxLayout.inflateData(data: List<Interests>) {
    removeAllViews()
    data.map { addView(InterestView(context).apply { setInterest(it.name) }) }
}
