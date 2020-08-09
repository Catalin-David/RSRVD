package com.halcyonmobile.rsrvd.shared

import android.annotation.SuppressLint
import android.location.Location
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.core.shared.Location as myLocation

@BindingAdapter(value = ["interests", "checkable"], requireAll = false)
fun FlexboxLayout.interests(data: List<Interests>?, checkable: Boolean?) = data?.map {
    addView(InterestView(context, checkable).apply { setInterest(it.name) })
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
        UserLocalRepository.location.first,
        UserLocalRepository.location.second,
        distances
    )

    val distanceFormatted =
        if (distances[0] > 1000) context.getString(R.string.kilometers, distances[0] / 1000)
        else context.getString(R.string.meters, distances[0])

    text = context.getString(R.string.distance_away, distanceFormatted)
} else {
    text = ""
}

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(imageUrl: Uri?) {
    Glide.with(this).asBitmap().load(imageUrl).error(R.mipmap.ic_launcher).into(this)
}
