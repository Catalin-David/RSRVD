package com.halcyonmobile.rsrvd.shared

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.location.Location
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.onboarding.InterestView
import java.text.SimpleDateFormat
import java.util.*
import com.halcyonmobile.rsrvd.core.shared.Location as myLocation

@BindingAdapter(value = ["interests", "checkable", "radio"], requireAll = false)
fun FlexboxLayout.interests(data: List<Interests>?, checkable: Boolean?, radio: Boolean?) {
    removeAllViews()

    val listener : (InterestView) -> Unit = {
        // Remove all selection
        this.iterator().forEach { child -> (child as InterestView).setChecked(false) }
        // Select only the clicked one
        it.setChecked(true)
    }

    data?.map { interest ->
        addView(InterestView(context, checkable).apply {
            setInterest(interest.name)

            if (radio == true) {
                setListener { listener(it) }
            }
        })
    }
}

@BindingAdapter(value = ["interest", "checkable"], requireAll = false)
fun FlexboxLayout.interest(interest: String, checkable: Boolean?) {
    removeAllViews()
    addView(InterestView(context, checkable).apply { setInterest(interest) })
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
fun ImageView.imageUrl(imageUrl: Uri?) = Glide.with(this).asBitmap().load(imageUrl).error(R.mipmap.ic_launcher).into(this)


@BindingAdapter("imageUrlString")
fun ImageView.imageUrlString(imageUrlString: String?) = Glide.with(this).asBitmap().load(imageUrlString).error(R.mipmap.ic_launcher).into(this)


@BindingAdapter("reservationDate")
fun TextView.reservationDate(dateString: String) =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateString)?.let { date ->
        Calendar.getInstance().apply {
            time = date
        }.let { calendar ->
            text = context.getString(
                R.string.reservation_date_format,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH).toMonth(context),
                calendar.get(Calendar.YEAR)
            )
        }
    }


@BindingAdapter("reservationHour")
fun TextView.reservationHour(dateString: String) =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateString)?.let { date ->
        Calendar.getInstance().apply {
            time = date
        }.let { calendar ->
            text = context.getString(
                R.string.reservation_hour_format,
                calendar.get(Calendar.HOUR).toDoubleDigit(context),
                calendar.get(Calendar.MINUTE).toDoubleDigit(context),
                calendar.get(Calendar.AM_PM).toAmPm(context)
            )
        }
    }

@BindingAdapter("tintByState")
fun ImageButton.tintByState(state: ReservationState){
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, when(state){
        ReservationState.CANCELLED -> R.color.error
        else -> R.color.primary
    })))
}
fun Int.toAmPm(context: Context) = if (this == Calendar.AM) context.resources.getString(R.string.am) else context.resources.getString(R.string.pm)

fun Int.toDoubleDigit(context: Context) = if (this in 0.until(10)) context.resources.getString(R.string.double_digit_format, this) else toString()

fun Int.toMonth(context: Context): String = context.resources.getStringArray(R.array.months)[this]