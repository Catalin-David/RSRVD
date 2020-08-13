package com.halcyonmobile.rsrvd.venuedetails

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Facilities

class FacilityView(context: Context) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.facility, this)
    private val imageView = view.findViewById<ImageView>(R.id.venue_facility_image)
    private val textView = view.findViewById<TextView>(R.id.venue_facility_name)
    private var facilitiesAndDrawables: HashMap<Facilities, Int> = HashMap()

    init {
        facilitiesAndDrawables[Facilities.PARKING] = R.drawable.ic_parking
        facilitiesAndDrawables[Facilities.RESTAURANT] = R.drawable.ic_restaurant
        facilitiesAndDrawables[Facilities.ACCESSIBLE] = R.drawable.ic_accessibility
        facilitiesAndDrawables[Facilities.TOILET] = R.drawable.ic_toilet
        facilitiesAndDrawables[Facilities.LOCKER] = R.drawable.ic_locker
        facilitiesAndDrawables[Facilities.SHOP] = R.drawable.ic_shop
        facilitiesAndDrawables[Facilities.SHOWER] = R.drawable.ic_shower
    }

    fun setFacility(facility: Facilities) {
        textView.text = facility.name

        imageView.setImageResource(
            if (facilitiesAndDrawables.containsKey(facility)) facilitiesAndDrawables[facility]!!
            else R.drawable.ic_toilet
        )
    }

}
