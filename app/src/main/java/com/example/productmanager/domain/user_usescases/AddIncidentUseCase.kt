package com.example.productmanager.domain.user_usescases

import com.example.productmanager.data.database.DataBaseIncidencesService
import com.example.productmanager.domain.model.entities.Incidence
import javax.inject.Inject

class AddIncidentUseCase @Inject constructor(private val dataBaseIncidencesService: DataBaseIncidencesService) {

    suspend operator fun invoke(incidence: Incidence): Boolean {

        return dataBaseIncidencesService.save(incidence)
    }
}