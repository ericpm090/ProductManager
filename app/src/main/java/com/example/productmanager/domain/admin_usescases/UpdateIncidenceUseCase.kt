package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseIncidencesService
import com.example.productmanager.domain.model.entities.Incidence
import javax.inject.Inject

class UpdateIncidenceUseCase @Inject constructor(private val db: DataBaseIncidencesService) {

    suspend operator fun invoke(incidence: Incidence): Boolean {

        return db.update(incidence)
    }
}