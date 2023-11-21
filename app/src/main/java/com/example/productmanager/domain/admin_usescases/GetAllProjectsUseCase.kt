package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseProjectService
import javax.inject.Inject

class GetAllProjectsUseCase @Inject constructor(private val db: DataBaseProjectService) {

    suspend operator fun invoke(): MutableList<String> {

        return db.getAllProjects()
    }
}