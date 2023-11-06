package com.example.productmanager.ui.login

import android.content.Intent
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productmanager.domain.CurrentUserUserCase
import com.example.productmanager.domain.LoginUseCase
import com.example.productmanager.domain.LoginWithGoogleUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    val currentUserUserCase: CurrentUserUserCase,
) : ViewModel() {

    val navigateToHomeUser = MutableLiveData<Boolean>()
    val navigateToSignInGoogle = MutableLiveData<Boolean>()

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    fun onLoginSelected(email: String, password: String) {

        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        }
    }
    fun onLoginGoogleSelected(data: Intent?) {
        if (data != null) {
            loginWithGoogle(data)
        }
    }
    fun getUser(): FirebaseUser? {
        val user = currentUserUserCase.invoke()
        return user
    }

    private fun loginUser(email: String, password: String) {
        val accountCreated = loginUseCase(email, password)
        navigateToHomeUser.postValue(accountCreated)
    }

    private fun loginWithGoogle(data: Intent?) {
        val accountCreated = loginWithGoogleUseCase(data)
        navigateToSignInGoogle.postValue(accountCreated)

    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()
    }

    private fun isValidEmail(email: String): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
    }


}