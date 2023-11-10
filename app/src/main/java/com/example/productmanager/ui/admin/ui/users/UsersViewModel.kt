package com.example.productmanager.ui.admin.ui.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productmanager.domain.DeleteUserUseCase
import com.example.productmanager.domain.ModifyUserUseCase
import com.example.productmanager.domain.SearchUserUseCase
import com.example.productmanager.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val modifyUserUseCase: ModifyUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    val searchUser = MutableLiveData<Employee?>()
    val modifyUser = MutableLiveData<Boolean>()
    val deleteUser = MutableLiveData<Boolean>()

    fun onSearchSelected(data: String) {
        val employee = searchUserUseCase(data)
        searchUser.postValue(employee)
    }

    fun onModifySelected(email: String, name: String, password: String) {
        val res = modifyUserUseCase(email, name, password)
        modifyUser.postValue(res)
    }

    fun onRemoveSelected(data: String) {
        val res = deleteUserUseCase(data)
        deleteUser.postValue(res)
    }

}

