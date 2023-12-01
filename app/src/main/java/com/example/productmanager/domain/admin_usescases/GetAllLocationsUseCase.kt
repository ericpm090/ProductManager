package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseLocationService
import javax.inject.Inject

class GetAllLocationsUseCase @Inject constructor(private val db: DataBaseLocationService) {

    suspend operator fun invoke(): MutableList<String> {

        return db.getAllLocations()
    }
}