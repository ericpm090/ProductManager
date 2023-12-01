package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseToolService
import javax.inject.Inject

class DeleteToolUseCase @Inject constructor(private val db: DataBaseToolService) {

    operator fun invoke(name: String): Boolean {

        return db.delete(name)
    }
}