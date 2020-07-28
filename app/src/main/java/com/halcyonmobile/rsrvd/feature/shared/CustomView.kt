package com.halcyonmobile.rsrvd.feature.shared

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

class CustomView(private val view: View) {
    fun createSnackbar(text: String) = Snackbar.make(view, text, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(Color.argb(240, 0, 0, 0))
        setTextColor(Color.WHITE)
    }
}