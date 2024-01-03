package com.example.productmanager.ui.admin.ui.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.admin_usescases.DeleteUserUseCase
import com.example.productmanager.domain.model.entities.Employee
import com.example.productmanager.domain.model.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val userService: UserService
) : ViewModel() {

    val searchUser = MutableLiveData<Employee?>()
    val modifyUser = MutableLiveData<Boolean>()
    val deleteUser = MutableLiveData<Boolean>()

    /*
    Search user by email. If found it post the result to fragment. If not post null.
     */
    fun onSearchSelected(email: String) {
        Log.i("onSearchSelected", email)
        if (email.isNotEmpty()) {
            //viewmodelScope to manage coroutines
            viewModelScope.launch {
                val employee = userService.getUser(email)
                if (employee != null) {
                    searchUser.postValue(withContext(Dispatchers.IO) { employee })
                } else {
                    searchUser.postValue(null)
                }
            }
        } else {
            searchUser.postValue(null)
        }

    }

    /*
    If all the arguments are not empty, save the data and post the result.
     */
    fun onModifySelected(email: String, name: String, password: String) {
        if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
            val res = userService.saveUser(
                Employee(
                    email = email,
                    name = name,
                    password = password
                )
            )
            modifyUser.postValue(res)
        } else {
            modifyUser.postValue(false)
        }

    }

    /*
    If data is not empty, call usercase and delete data. Post result.
     */
    fun onRemoveSelected(data: String) {
        if (data.isNotEmpty()) {
            val res = deleteUserUseCase(data)
            deleteUser.postValue(res)
        } else {
            deleteUser.postValue(false)
        }

    }

}

