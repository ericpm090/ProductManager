package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseUserService
import com.example.productmanager.domain.model.entities.Employee
import javax.inject.Inject

class ModifyUserUseCase @Inject constructor(private val db: DataBaseUserService) {

    operator fun invoke(employee: Employee): Boolean {

        return db.save(employee)
    }
}