package com.example.productmanager.domain

import com.example.productmanager.data.database.AuthService
import com.example.productmanager.domain.model.Employee
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authService: AuthService) {

    suspend operator fun invoke(employee: Employee): Boolean {
        return authService.createAccount(employee)

    }
}