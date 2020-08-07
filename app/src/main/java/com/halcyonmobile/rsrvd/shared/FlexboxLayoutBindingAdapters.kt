package com.halcyonmobile.rsrvd.shared

import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.core.shared.Facilities
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.venuedetails.FacilityView

@BindingAdapter("facilities")
fun FlexboxLayout.facilities(facilities: List<Facilities>?) {
    facilities?.map { addView(FacilityView(context).apply { setFacility(it) }) }
}
