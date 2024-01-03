package com.example.productmanager.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productmanager.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val logOutUserCase: LogoutUseCase
) : ViewModel() {

    private val _userEmail = MutableLiveData<String>()


    val userMail: LiveData<String> get() = _userEmail
    fun setUserEmail(email: String) {
        _userEmail.value = email

    }

    fun logOut() {
        logOutUserCase.invoke()
    }


}