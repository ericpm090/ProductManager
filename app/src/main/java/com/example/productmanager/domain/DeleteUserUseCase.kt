package com.example.productmanager.domain

import com.example.productmanager.data.database.DataBaseService
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val db: DataBaseService) {

    operator fun invoke(email: String): Boolean {

        return db.deleteUser(email)
    }
}