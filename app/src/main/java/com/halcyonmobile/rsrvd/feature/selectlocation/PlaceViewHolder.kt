package com.halcyonmobile.rsrvd.feature.selectlocation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class PlaceViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun setLocation(location: Location) {
        view.findViewById<TextView>(R.id.location_name).text = location.toString()

//        view.setOnClickListener {
//            listener()
//        }
    }
}
