package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseToolService
import javax.inject.Inject

class AddToolUseCase @Inject constructor(private val db: DataBaseToolService) {

    suspend operator fun invoke(
        barcode: String,
        name: String,
        project: String,
        location: String,
        photo: String,
        type:String,
        status: String
    ): Boolean {

        return db.save(barcode, name, project, location, photo,type,status)
    }
}