package com.halcyonmobile.rsrvd.venuedetails

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Facilities

class FacilityView(context: Context) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.facility, this)

    fun setFacility(facility: Facilities) {
        view.findViewById<TextView>(R.id.venue_facility_name).text = facility.name
        val imageView = view.findViewById<ImageView>(R.id.venue_facility_image)

        when (facility) {
            Facilities.PARKING -> imageView.setImageResource(R.drawable.ic_parking)
            Facilities.RESTAURANT -> imageView.setImageResource(R.drawable.ic_restaurant)
            Facilities.ACCESSIBLE -> imageView.setImageResource(R.drawable.ic_accessibility)
            Facilities.TOILET -> imageView.setImageResource(R.drawable.ic_toilet)
            else -> imageView.setImageResource(R.drawable.ic_toilet)
        }
    }

}
