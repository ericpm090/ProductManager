package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseLocationService
import com.example.productmanager.domain.model.entities.Location
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(private val db: DataBaseLocationService) {

    suspend operator fun invoke(name: String): Location? {

        return db.get(name)
    }
}