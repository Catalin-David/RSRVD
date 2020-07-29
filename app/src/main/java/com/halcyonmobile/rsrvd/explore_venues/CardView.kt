package com.halcyonmobile.rsrvd.explore_venues

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.halcyonmobile.rsrvd.R

class CardView (context: Context) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.card, this)

    init {
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 20, 0)
        this.layoutParams = params
    }

    fun setSomething(something: String) {
    }
}