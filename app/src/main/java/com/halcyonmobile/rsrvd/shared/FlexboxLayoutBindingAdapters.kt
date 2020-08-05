package com.halcyonmobile.rsrvd.shared

import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.core.shared.Facilities
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.venuedetails.FacilityView

@BindingAdapter("interests", "checkable", requireAll = false)
fun FlexboxLayout.inflateData(interests: List<Interests>, checkable: Boolean?) {
    removeAllViews()
    val isCheckable: Boolean = checkable ?: true
    interests.map { addView(InterestView(context, isCheckable).apply { setInterest(it.name) }) }
}

@BindingAdapter("facilities")
fun FlexboxLayout.inflateData(facilities: List<Facilities>) {
    facilities.map { addView(FacilityView(context).apply { setFacility(it.name) }) }
}
