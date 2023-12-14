package com.example.productmanager.domain

import com.example.productmanager.data.database.DataBaseToolService
import com.example.productmanager.domain.model.entities.Tool
import javax.inject.Inject

class SearchToolUseCase @Inject constructor(private val db: DataBaseToolService) {

    suspend operator fun invoke(barcode: String): Tool? {

        return db.get(barcode)
    }
}