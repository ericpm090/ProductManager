package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseToolService
import com.example.productmanager.domain.model.entities.Tool
import javax.inject.Inject

class AddToolUseCase @Inject constructor(private val db: DataBaseToolService) {

    suspend operator fun invoke(
        tool: Tool
    ): String {

        return db.save(tool)
    }
}