package com.halcyonmobile.rsrvd.explorevenues.filter

object Times {
    val months = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    private const val HOURS_START: Int = 0
    private const val HOURS_END: Int = 2400
    private const val HOURS_STEP: Int = 50

    val hours = (HOURS_START until HOURS_END step HOURS_STEP).toList()
}