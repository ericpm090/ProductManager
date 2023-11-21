package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseToolService
import javax.inject.Inject

class AddToolUseCase @Inject constructor(private val db: DataBaseToolService) {

    operator fun invoke(name: String, project: String, description: String): Boolean {

        return db.save(name, project, description)
    }
}