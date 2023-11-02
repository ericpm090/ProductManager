package com.example.productmanager.data.database

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthService @Inject constructor(private val firebase: FirebaseAuth) {

    fun login(email: String, password: String): Boolean {

        val res = firebase.signInWithEmailAndPassword(email, password)
        return res.isSuccessful

    }
    suspend fun createAccount(email: String, password: String): Boolean {
        Log.i("NEW_ACCOUNT", "createAccount for " + email)
        firebase.createUserWithEmailAndPassword(email, password).await()
        return true
    }


}