package com.example.productmanager.domain

import com.example.productmanager.data.database.AuthService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class CurrentUserUserCase@Inject constructor(private val authService: AuthService) {
    operator fun invoke(): FirebaseUser? {
        return authService.getCurrentUser()
    }
}