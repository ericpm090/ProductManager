package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseProjectService
import com.example.productmanager.domain.model.entities.Project
import javax.inject.Inject

class SearchProjectUseCase @Inject constructor(private val db: DataBaseProjectService) {

    suspend operator fun invoke(name: String): Project? {

        return db.get(name)
    }
}