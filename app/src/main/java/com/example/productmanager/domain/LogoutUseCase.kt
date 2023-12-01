package com.example.productmanager.domain

import com.example.productmanager.data.database.AuthService
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authService: AuthService) {

    operator fun invoke(): Boolean {

        return authService.logout()
    }

}