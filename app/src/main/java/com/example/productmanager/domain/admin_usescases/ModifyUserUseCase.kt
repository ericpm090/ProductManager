package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseService
import javax.inject.Inject

class ModifyUserUseCase @Inject constructor(private val db: DataBaseService) {

    operator fun invoke(email: String, name: String, password: String): Boolean {

        return db.saveUser(email, name, password)
    }
}