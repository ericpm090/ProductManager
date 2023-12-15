package com.example.productmanager.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor():ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userMail: LiveData<String> get() = _userEmail

    fun setUserEmail(email:String){
        Log.i("UseDataViewModel", "Setting $email in useractivity ")
        _userEmail.value = email

    }
}