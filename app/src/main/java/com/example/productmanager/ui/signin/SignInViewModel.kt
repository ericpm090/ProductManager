package com.example.productmanager.ui.signin

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
class SignInViewModel @Inject constructor(val signInUseCase: SignInUseCase) : ViewModel() {

    val navigateToHomeUser = MutableLiveData<Boolean>()

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    fun onSignInSelected(employee: Employee) {
        if (isValidEmail(employee.email) && isValidPassword(employee.password)) {
            signIn(employee)
        }

    }

    private fun signIn(employee: Employee) {
        //trabaja en el hilo principal
        viewModelScope.launch {
            //IO = trabaja en otro hilo para no bloquear el principal
            val res = withContext(Dispatchers.IO) { signInUseCase(employee) }
            navigateToHomeUser.postValue(res)

        }

    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH || password.isNotEmpty()
    }

    private fun isValidEmail(email: String): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isNotEmpty()
    }
}


