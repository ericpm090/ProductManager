package com.example.productmanager.domain

import com.example.productmanager.data.database.AuthService
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authService: AuthService) {

    operator fun invoke(email: String, password: String): Boolean {

        return authService.login(email, password)
    }

}