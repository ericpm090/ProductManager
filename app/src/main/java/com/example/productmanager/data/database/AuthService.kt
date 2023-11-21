package com.example.productmanager.data.database

import android.content.Intent
import android.util.Log
import com.example.productmanager.domain.model.Employee
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val firebase: FirebaseAuth,
    private val dataBaseUserService: DataBaseUserService
) {

    fun login(email: String, password: String): Boolean {

        val res = firebase.signInWithEmailAndPassword(email, password)
        return res.isSuccessful

    }

    suspend fun createAccount(employee: Employee): Boolean {
        Log.i("NEW_ACCOUNT", "createAccount for ${employee.email}")

        firebase.createUserWithEmailAndPassword(employee.email, employee.password).await()
        dataBaseUserService.save(employee.email, employee.name, employee.password)
        return true
    }

    fun createAccountWithCredential(data: Intent?): Boolean {
        val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
        if (result != null) {
            if (result.isSuccess) {
                val credential =
                    GoogleAuthProvider.getCredential(result.signInAccount?.idToken, null)
                firebase.signInWithCredential(credential)
                dataBaseUserService.save(getCurrentUser()?.email.toString())

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