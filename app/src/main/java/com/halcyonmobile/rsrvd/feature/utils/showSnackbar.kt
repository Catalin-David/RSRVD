package com.halcyonmobile.rsrvd.feature.utils

import android.graphics.Color
import android.view.View
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(text: String) =
    Snackbar.make(

        text,
        Snackbar.LENGTH_LONG
    ).apply {
        setBackgroundTint(Color.argb(240, 0, 0, 0))
        setTextColor(Color.WHITE)
    }