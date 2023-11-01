package com.example.productmanager.domain

import com.example.productmanager.data.database.AuthService
import com.example.productmanager.domain.model.Employee
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authService: AuthService)  {


    operator fun invoke(email: String, password: String):Boolean {
        return  authService.createAccount(email,password)


    }
}