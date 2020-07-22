package com.halcyonmobile.rsrvd.signin.event

import android.content.Intent

interface ActivityNavigation {
    fun startActivityForResult(intent: Intent?, requestCode: Int)
}