package com.halcyonmobile.rsrvd.core.shared

enum class Interests {
    RUNNING, WORKOUT, YOGA, FOOTBALL, BASKETBALL, TENNIS, BADMINTON, HANDBALL, VOLLEYBALL, BOWLING, TABLETENNIS;

    companion object {
        fun getInterestBasedOnName(s: String): Interests =
            when (s) {
                "Running" -> RUNNING
                "Workout" -> WORKOUT
                "Yoga" -> YOGA
                "Football" -> FOOTBALL
                "Basketball" -> BASKETBALL
                "Tennis" -> TENNIS
                "Badminton" -> BADMINTON
                "Handball" -> HANDBALL
                "Bowling" -> BOWLING
                "Volleyball" -> VOLLEYBALL
                "Table tennis" -> TABLETENNIS
                else -> RUNNING
            }
    }
}