package com.iuliamariabirsan.core.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object UserRepository {
    private const val IS_USER_LOGGED_IN_KEY = "com.halcyonmobile.rsrvd.IS_USER_LOGGED_IN_KEY"

    private lateinit var sharedPreferences: SharedPreferences

    fun getInstance(context: Context): UserRepository{
        if(!::sharedPreferences.isInitialized){
            synchronized(UserRepository::class.java){
                if(!::sharedPreferences.isInitialized){
                    sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                }
            }
        }
        return this
    }

    var isUserLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY, false)
        set(userStatus){
            val editor = sharedPreferences.edit()
            with(editor){
                putBoolean(IS_USER_LOGGED_IN_KEY, userStatus)
                apply()
            }
        }
}
// SECOND METHOD OF IMPLEMENTING:
//class UserRepository private constructor() {
//
//    companion object{
//        private val userRepository = UserRepository()
//        private lateinit var sharedPreferences: SharedPreferences
//        private var IS_USER_LOGGED_IN_KEY = "com.halcyonmobile.rsrvd.IS_USER_LOGGED_IN_KEY"
//
//        fun getInstance(context: Context): UserRepository{
//            if(!::sharedPreferences.isInitialized){
//                synchronized(UserRepository::class.java){
//                    if(!::sharedPreferences.isInitialized){
//                        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
//                    }
//                }
//            }
//            return userRepository
//        }
//    }
//
//    var isUserLoggedIn: Boolean
//        get() = sharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY, false)
//        set(userStatus){
//            val editor = sharedPreferences.edit()
//            with(editor){
//                putBoolean(IS_USER_LOGGED_IN_KEY, userStatus)
//                apply()
//            }
//        }
//}