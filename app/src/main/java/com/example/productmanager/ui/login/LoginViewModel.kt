package com.example.productmanager.ui.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productmanager.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase) : ViewModel() {

    val navigateToHomeUser = MutableLiveData<String>()

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    fun onLoginSelected(email: String, password: String) {

        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        val accountCreated = loginUseCase(email, password)

        if (accountCreated) {
            navigateToHomeUser.postValue(email)
        } else {
            navigateToHomeUser.postValue("FALSE")
        }

    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()
    }

    private fun isValidEmail(email: String): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
    }
}