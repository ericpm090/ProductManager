package com.example.productmanager.domain

import com.example.productmanager.data.database.DataBaseService
import com.example.productmanager.domain.model.Employee
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(private val db: DataBaseService) {

    operator fun invoke(email: String): Employee? {

        return db.getUser(email)
    }
}