package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseLocationService
import com.example.productmanager.domain.model.entities.Location
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(private val db: DataBaseLocationService) {

    suspend operator fun invoke(location: Location): Boolean {

        return db.save(location)
    }
}