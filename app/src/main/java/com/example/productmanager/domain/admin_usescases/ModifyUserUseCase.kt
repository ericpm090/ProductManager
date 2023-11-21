package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseUserService
import javax.inject.Inject

class ModifyUserUseCase @Inject constructor(private val db: DataBaseUserService) {

    operator fun invoke(email: String, name: String, password: String): Boolean {

        return db.save(email, name, password)
    }
}