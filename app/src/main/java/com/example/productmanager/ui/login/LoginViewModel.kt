package com.example.productmanager.ui.login

import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.LoginUseCase
import com.example.productmanager.domain.LoginWithGoogleUseCase
import com.example.productmanager.ui.signin.SignInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val loginWithGoogleUseCase: LoginWithGoogleUseCase

) : ViewModel() {

    val navigateToHomeUser = MutableLiveData<Boolean>()
    val navigateToSignInGoogle = MutableLiveData<String?>()

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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val accountCreated = loginUseCase(email, password)
                Log.i("loginuser",email+ " " + password + " " + accountCreated)
                navigateToHomeUser.postValue(accountCreated)
            }

        }

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

    fun isValidPassword(password: String): Boolean {

        return password.length >= SignInViewModel.MIN_PASSWORD_LENGTH && password.isNotEmpty()
    }

    fun isValidEmail(email: String): Boolean {

        //if(email==null) return false

        //emailPattern = Patterns.EMAIL_ADDRESS
        //val emailPattern: String =
        //  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        //Log.i("isValidEmail", email.matches(emailPattern.toRegex()).toString())
        //return email.matches(emailPattern.toRegex())

        //disable Patterns for testing.
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
    }



}