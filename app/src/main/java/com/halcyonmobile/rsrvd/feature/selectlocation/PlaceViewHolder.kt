package com.halcyonmobile.rsrvd.feature.selectlocation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class PlaceViewHolder(private val view: View, private val listener: (Location) -> Unit) : RecyclerView.ViewHolder(view) {
    fun setLocation(location: Location) {
        view.findViewById<TextView>(R.id.location_name).text = location.name
        view.findViewById<TextView>(R.id.location_details).text = location.details

        view.setOnClickListener { listener(location) }
    }
}
