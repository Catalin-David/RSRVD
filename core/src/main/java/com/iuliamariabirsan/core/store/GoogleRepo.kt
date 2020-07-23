package com.iuliamariabirsan.core.store

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.iuliamariabirsan.core.repository.UserRepository


class GoogleRepo {

     fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
         try {
             val account = completedTask.getResult(ApiException::class.java)
             val idToken = account!!.idToken

             Log.w(TAG, "token $idToken")

             if (idToken != null) {
                 UserRepository.userSignIn(idToken)
             }
         } catch (e: ApiException) {
             Log.w(TAG, "handleSignInResult:error", e)
         }
    }

}