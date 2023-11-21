package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseLocationService
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(private val db: DataBaseLocationService) {

    operator fun invoke(name: String): Boolean {

        return db.delete(name)
    }
}