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
import com.halcyonmobile.rsrvd.core.shared.Facilities
import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.core.venues.dto.Open
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.venuedetails.FacilityView
import java.text.SimpleDateFormat
import java.util.*
import com.halcyonmobile.rsrvd.core.shared.Location as myLocation

@BindingAdapter(value = ["interests", "checkable", "radio"], requireAll = false)
fun FlexboxLayout.interests(data: List<Interests>?, checkable: Boolean?, radio: Boolean?) {
    removeAllViews()

    val listener: (InterestView) -> Unit = {
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

@BindingAdapter("facilities")
fun FlexboxLayout.facilities(facilities: List<Facilities>?) {
    facilities?.map { addView(FacilityView(context).apply { setFacility(it) }) }
}

@BindingAdapter(value = ["interest", "checkable"], requireAll = false)
fun FlexboxLayout.interest(interest: String?, checkable: Boolean?) {
    interest?.let {
        removeAllViews()
        addView(InterestView(context, checkable).apply { setInterest(it) })
    }
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
fun TextView.reservationDate(dateString: String?) =
    dateString?.let {
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
    }


@BindingAdapter("reservationHour")
fun TextView.reservationHour(dateString: String?) {
    dateString?.let {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'", Locale.getDefault())
            .apply { timeZone = TimeZone.getTimeZone("GMT") }
            .parse(dateString)?.let { date ->
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
    }
}


@BindingAdapter("tintByState")
fun ImageButton.tintByState(state: ReservationState?) {
    state?.let {
        ImageViewCompat.setImageTintList(
            this, ColorStateList.valueOf(
                ContextCompat.getColor(
                    context, when (state) {
                        ReservationState.CANCELLED -> R.color.error
                        else -> R.color.primary
                    }
                )
            )
        )
    }
}

fun Int.toAmPm(context: Context) = if (this == Calendar.AM) context.resources.getString(R.string.am) else context.resources.getString(R.string.pm)

fun Int.toDoubleDigit(context: Context) = if (this in 0.until(10)) context.resources.getString(R.string.double_digit_format, this) else toString()

fun Int.toMonth(context: Context): String = context.resources.getStringArray(R.array.months)[this]

private fun getDay(context: Context, i: Int): String =
    listOf(
        context.getString(R.string.mon),
        context.getString(R.string.tue),
        context.getString(R.string.wed),
        context.getString(R.string.thu),
        context.getString(R.string.fri),
        context.getString(R.string.sat),
        context.getString(R.string.sun)
    )[i]

private fun getHour(context: Context, f: Float): String {
    val minutes: Int = (f % 1 * 100).toInt()

    return """${(f % 12).toInt()}:${if (minutes < 10) "0" else ""}$minutes ${if (f < 12) {
        context.getString(R.string.am)
    } else {
        context.getString(R.string.pm)
    }}""".trimMargin()
}

@BindingAdapter("getVenueProgram")
fun TextView.getVenueProgram(open: Open?) {
    open?.let {
        val listOpenHours = mutableListOf(
            open.dayZero,
            open.dayOne,
            open.dayTwo,
            open.dayThree,
            open.dayFour,
            open.dayFive,
            open.daySix
        )

        var start = open.dayZero.start
        var end = open.dayZero.end
        val listPositions: ArrayList<Int> = ArrayList()

        listOpenHours.map {
            if (it.start != start || it.end != end) {
                start = it.start
                end = it.end

                listPositions.add(listOpenHours.indexOf(it))
            }
        }

        var result = ""
        for (i in 0 until listPositions.size) {
            val day = getDay(context, listPositions[i] - 1)
            val hStart = getHour(context, listOpenHours[listPositions[i]].start)
            val hEnd = getHour(context, listOpenHours[listPositions[i]].end)

            result +=
                if ((i < listPositions.size - 1) && (listPositions[i] - listPositions[i + 1]) > 1)
                    "$day - ${getDay(context, listPositions[i])}, $hStart - $hEnd\n"
                else "$day, $hStart - $hEnd\n"
        }

        val index = if (listPositions.isEmpty()) 0 else listPositions.size - 1
        val day = if (listPositions.isEmpty()) getDay(context, index) else getDay(context, listPositions[index])
        val hStart = getHour(context, listOpenHours[index].start)
        val hEnd = getHour(context, listOpenHours[index].end)
        result += "$day - ${getDay(context, 6)}, $hStart - ${hEnd}\n"

        this.text = result
    }
}

@BindingAdapter("getVenueLocation")
fun TextView.getVenueLocation(location: myLocation?) {
    this.text = context.resources.getString(R.string.location_description, location?.name, location?.details)
}

@BindingAdapter("imageUrlDetail")
fun ImageView.imageUrlDetail(imageUrl: String?) = Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_baseline_cloud_download).into(this)

