package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseUserService
import com.example.productmanager.domain.model.entities.Employee
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val db: DataBaseUserService) {

    suspend operator fun invoke(): MutableList<Employee> {

        return db.getAll()
    }
}