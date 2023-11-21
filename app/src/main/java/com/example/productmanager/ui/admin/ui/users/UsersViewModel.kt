package com.example.productmanager.ui.admin.ui.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.admin_usescases.DeleteUserUseCase
import com.example.productmanager.domain.admin_usescases.ModifyUserUseCase
import com.example.productmanager.domain.admin_usescases.SearchUserUseCase
import com.example.productmanager.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        if(data.isNotEmpty()){
            viewModelScope.launch {
                val employee = withContext(Dispatchers.IO) {searchUserUseCase(data)}
                searchUser.postValue(employee)
            }
        }


    }

    fun onModifySelected(email: String, name: String, password: String) {
        if(email.isNotEmpty() && name.isNotEmpty()){
            if(name.isNotEmpty()){
                val res = modifyUserUseCase(email, name, password)
                modifyUser.postValue(res)
            }
        }

    }

    fun onRemoveSelected(data: String) {
        if(data.isNotEmpty()){
            val res = deleteUserUseCase(data)
            deleteUser.postValue(res)
        }

    }

}

