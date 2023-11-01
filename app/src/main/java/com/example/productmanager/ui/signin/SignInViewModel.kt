package com.example.productmanager.ui.signin

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productmanager.domain.LoginUseCase
import com.example.productmanager.domain.SignInUseCase
import com.example.productmanager.domain.model.Employee
import com.example.productmanager.ui.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(val signInUseCase: SignInUseCase):ViewModel() {


   val navigateToHomeUser = MutableLiveData<Boolean>()


    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    fun onSignInSelected(employee: Employee){
        if(isValidEmail(employee.email) && isValidPassword(employee.password)){
            signIn(employee.email,employee.password)
        }

    }

    private fun signIn(email:String, password: String):Boolean {
        Log.i("","Email and password are correct")
        return signInUseCase(email,password)

    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= SignInViewModel.MIN_PASSWORD_LENGTH || password.isEmpty()
    }

    private fun isValidEmail(email: String): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
    }
}


