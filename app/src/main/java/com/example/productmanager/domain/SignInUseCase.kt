package com.example.productmanager.domain

import com.example.productmanager.data.database.AuthService
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authService: AuthService) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return authService.createAccount(email, password)


    }
}