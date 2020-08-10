package com.halcyonmobile.rsrvd.makereservation

import android.content.Context
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.halcyonmobile.rsrvd.R

class HourView(context: Context) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.hour_radio_button, this)
    private val radioButton = view.findViewById<RadioButton>(R.id.hour_radio_button)

    init {
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 16, 0)
        this.layoutParams = params
    }

    fun setHour(hour: String) {
        radioButton.text = hour
    }

    fun isChecked() = radioButton?.isChecked ?: false

    fun setChecked(state: Boolean) {
        radioButton?.isChecked = state
    }
}
