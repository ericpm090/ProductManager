package com.example.productmanager.domain

import android.content.Intent
import com.example.productmanager.data.database.AuthService
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(private val auth: AuthService) {

    operator fun invoke(data: Intent?): Boolean {

        return auth.createAccountWithCredential(data)
    }
}