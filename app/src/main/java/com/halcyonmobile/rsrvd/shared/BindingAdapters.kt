package com.halcyonmobile.rsrvd.shared

import android.annotation.SuppressLint
import android.location.Location
import com.halcyonmobile.rsrvd.selectlocation.Location as myLocation
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.onboarding.Interests

@BindingAdapter("interests")
fun FlexboxLayout.interests(data: List<Interests>) = data.map {
    addView(InterestView(context).apply { setInterest(it.name) })
}

@BindingAdapter("visible")
fun View.visible(visibility: Boolean) {
    isVisible = visibility
}

@SuppressLint("SetTextI18n")
@BindingAdapter("formattedDistance")
fun TextView.formattedDistance(location: myLocation?) = if (location != null) {
    val distances = FloatArray(1)

    Location.distanceBetween(
        location.latitude,
        location.longitude,
        UserRepository.location.first,
        UserRepository.location.second,
        distances
    )

    val distanceFormatted =
        if (distances[0] > 1000) "${"%.2f".format(distances[0] / 1000)}km"
        else "${distances[0]}m"

    text = " / $distanceFormatted away"
} else {
    text = ""
}
