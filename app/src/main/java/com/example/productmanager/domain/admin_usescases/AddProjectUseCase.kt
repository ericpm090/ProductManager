package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseProjectService
import javax.inject.Inject

class AddProjectUseCase @Inject constructor(private val db: DataBaseProjectService) {

    operator fun invoke(name: String): Boolean {

        return db.save(name)
    }
}