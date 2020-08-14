package com.halcyonmobile.rsrvd.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.halcyonmobile.rsrvd.R

@SuppressLint("ViewConstructor")
class InterestView(context: Context, checkable: Boolean?) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.interest, this)
    private val button = view.findViewById<CheckBox>(R.id.interest_button)

    init {
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val margin = view.context.resources.getDimensionPixelSize(R.dimen.default_padding)
        params.setMargins(0, 0, margin, margin)
        this.layoutParams = params

        checkable?.let { button.isClickable = it }
    }

    fun setInterest(interest: String) {
        button.text = interest
    }

    fun setListener(callback: (InterestView) -> Unit) {
        button.setOnClickListener { callback(this) }
    }

    fun isChecked() = button?.isChecked ?: false

    fun setChecked(state: Boolean) {
        button?.isChecked = state
    }
}