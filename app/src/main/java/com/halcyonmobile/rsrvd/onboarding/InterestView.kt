package com.halcyonmobile.rsrvd.onboarding

import android.content.Context
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.halcyonmobile.rsrvd.R

class InterestView(context: Context) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.interest, this)
    private val button = view.findViewById<CheckBox>(R.id.interest_button)

    init {
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 25, 25)
        this.layoutParams = params
    }

    constructor(context: Context, isCheckable: Boolean): this(context){
        if(!isCheckable){
            button.isClickable = false
        }
    }

    fun setInterest(interest: String) {
        button.text = interest
    }

    fun isChecked() = button?.isChecked ?: false

    fun setChecked(state: Boolean) {
        button?.isChecked = state
    }
}