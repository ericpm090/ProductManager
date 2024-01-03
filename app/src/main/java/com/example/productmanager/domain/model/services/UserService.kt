package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.ModifyUserUseCase
import com.example.productmanager.domain.admin_usescases.SearchUserUseCase
import com.example.productmanager.domain.model.entities.Employee
import javax.inject.Inject

class UserService @Inject constructor(
    private val modifyUserUseCase: ModifyUserUseCase,
    private val searchUseUseCase: SearchUserUseCase
) {


    fun saveUser(employee: Employee): Boolean {
        return modifyUserUseCase(employee)
    }

    suspend fun getUser(email: String): Employee? {
        val res = searchUseUseCase(email)
        return res
    }


}