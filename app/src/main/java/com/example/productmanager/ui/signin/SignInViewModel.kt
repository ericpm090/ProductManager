package com.example.productmanager.ui.signin

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.SignInUseCase
import com.example.productmanager.domain.model.entities.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    val navigateToHomeUser = MutableLiveData<Boolean>()
    val exceptionsSignIn = MutableLiveData<String>()

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    fun onSignInSelected(email: String, name: String, password: String) {

        if (isValidEmail(email) && isValidPassword(password)) {

            signIn(email, name, password)
        } else {
            navigateToHomeUser.postValue(false)
        }

    }

    private fun signIn(email: String, name: String, password: String) {
        //trabaja en el hilo principal
        viewModelScope.launch {

            try {
                withContext(Dispatchers.IO) {

                    val res = signInUseCase(
                        Employee(
                            email = email,
                            name = name,
                            password = password
                        )
                    )
                    Log.i(
                        "isValidPassword", res.toString()
                    )

                    navigateToHomeUser.postValue(res)
                }
            } catch (e: Exception) {
                exceptionsSignIn.postValue(e.message.toString())
            }


        }

    }

    fun isValidPassword(password: String): Boolean {

        return password.length >= MIN_PASSWORD_LENGTH && password.isNotEmpty()
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


