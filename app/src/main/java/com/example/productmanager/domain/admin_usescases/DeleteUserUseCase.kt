package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseUserService
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val db: DataBaseUserService) {

    operator fun invoke(email: String): Boolean {

        return db.deleteUser(email)
    }
}