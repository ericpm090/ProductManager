package com.example.productmanager.domain.model.services

import android.util.Log
import com.example.productmanager.domain.admin_usescases.GetIncidencesUseCase
import com.example.productmanager.domain.admin_usescases.UpdateIncidenceUseCase
import com.example.productmanager.domain.model.entities.Incidence
import com.example.productmanager.domain.model.entities.IncidenceStatus
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.domain.user_usescases.AddIncidentUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class IncidencesService @Inject constructor(
    private val addIncidenceUseCase: AddIncidentUseCase,
    private val getAllIncidencesUseCase: GetIncidencesUseCase,
    private val updateIncidenceUseCase: UpdateIncidenceUseCase
) {


    fun createIncidence(email: String, tool: Tool, description: String): Incidence {

        Log.i("InvicencesService", "Tool founded")
        return Incidence(
            id = "",
            employee = email,
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                .toString(),
            id_tool = tool.barcode,
            tool_name = tool.name,
            description = description,
            status = IncidenceStatus.PENDING.toString()
        )


    }

    suspend fun saveIncidence(incidence: Incidence): Boolean {
        return addIncidenceUseCase(incidence)
    }

    suspend fun getIncidences(): MutableList<Incidence> {
        return getAllIncidencesUseCase()
    }

    suspend fun solveIncidence(incidence:Incidence): Boolean {
        incidence.status = IncidenceStatus.SOLVED.toString()
        return updateIncidence(incidence)
    }

    suspend fun updateIncidence(incidence:Incidence):Boolean{
        return updateIncidenceUseCase(incidence)
    }

    suspend fun getIncidencesByEmail(email:String): MutableList<Incidence> {

        return getIncidences().filter { incidence -> incidence.employee.equals(email) }.toMutableList()
    }
}
