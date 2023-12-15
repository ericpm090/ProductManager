package com.example.productmanager.ui.login

import android.content.Intent
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.LoginUseCase
import com.example.productmanager.domain.LoginWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val loginWithGoogleUseCase: LoginWithGoogleUseCase,
) : ViewModel() {

    val navigateToHomeUser = MutableLiveData<Boolean>()
    val navigateToSignInGoogle = MutableLiveData<String?>()

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    fun onLoginSelected(email: String, password: String) {

        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        } else {
            navigateToHomeUser.postValue(false)
        }
    }

    fun onLoginGoogleSelected(data: Intent?) {
        if (data != null) {
            loginWithGoogle(data)
        }
    }


    private fun loginUser(email: String, password: String) {
        val accountCreated = loginUseCase(email, password)
        navigateToHomeUser.postValue(accountCreated)
    }

    private fun loginWithGoogle(data: Intent?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val accountCreated = loginWithGoogleUseCase(data)
                if (accountCreated != "") {
                    navigateToSignInGoogle.postValue(accountCreated)
                }
            }
        }


    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()
    }

    private fun isValidEmail(email: String): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
    }


}