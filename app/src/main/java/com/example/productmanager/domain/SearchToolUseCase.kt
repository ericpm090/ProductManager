package com.example.productmanager.domain

import com.example.productmanager.data.database.DataBaseToolService
import com.example.productmanager.domain.model.Tool
import javax.inject.Inject

class SearchToolUseCase @Inject constructor(private val db: DataBaseToolService) {

    suspend operator fun invoke(name: String): Tool? {

        return db.get(name)
    }
}