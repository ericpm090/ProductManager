package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseIncidencesService
import com.example.productmanager.domain.model.entities.Incidence
import javax.inject.Inject

class GetIncidencesUseCase @Inject constructor(private val db: DataBaseIncidencesService) {

    suspend operator fun invoke(): MutableList<Incidence> {

        return db.getAll()
    }
}