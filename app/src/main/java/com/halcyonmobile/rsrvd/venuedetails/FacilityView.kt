package com.halcyonmobile.rsrvd.venuedetails

import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.halcyonmobile.rsrvd.R

class FacilityView(context: Context) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.facility, this)

    init {
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(24, 8, 24, 8)
        this.layoutParams = params
    }

    fun setFacility(facility: String) {
        findViewById<TextView>(R.id.venue_facility_name).text = facility

        when (facility) {
            "PARKING" -> {
                findViewById<TextView>(R.id.venue_facility_drawable)
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_parking, 0, 0, 0)
            }
            "RESTAURANT" -> {
                findViewById<TextView>(R.id.venue_facility_drawable)
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_restaurant, 0, 0, 0)
            }
            "ACCESSIBLE" -> {
                findViewById<TextView>(R.id.venue_facility_drawable)
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_accessibility, 0, 0, 0)
            }
            "TOILET" -> {
                findViewById<TextView>(R.id.venue_facility_drawable)
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_toilet, 0, 0, 0)
            }
            else -> {
                findViewById<TextView>(R.id.venue_facility_drawable)
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_toilet, 0, 0, 0)
            }
        }
    }

}
