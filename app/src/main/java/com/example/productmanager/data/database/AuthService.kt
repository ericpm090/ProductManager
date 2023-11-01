package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.Employee
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Provides
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log


class AuthService @Inject constructor(private val firebase:FirebaseAuth){


    fun login(email:String, password:String):Boolean {

        var res = firebase.signInWithEmailAndPassword(email, password)
        return res.isSuccessful

    }

    fun createAccount(email: String, password: String): Boolean {
        val res =  firebase.createUserWithEmailAndPassword(email, password)
        return res.isSuccessful
    }

    fun getUserId():String? = firebase.currentUser?.uid





}