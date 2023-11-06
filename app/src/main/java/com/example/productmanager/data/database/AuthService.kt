package com.example.productmanager.data.database

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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

    fun createAccountWithCredential(data: Intent?): Boolean {

        val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
        if (result != null) {
            if (result.isSuccess) {
                val credential =
                    GoogleAuthProvider.getCredential(result.signInAccount?.idToken, null)
                firebase.signInWithCredential(credential)
            }
        }

        return result!!.isSuccess
    }


    fun logout(): Boolean {
        firebase.signOut()
        return true
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebase.currentUser

    }


}