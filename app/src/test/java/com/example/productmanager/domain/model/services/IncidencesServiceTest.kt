package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.GetIncidencesUseCase
import com.example.productmanager.domain.admin_usescases.UpdateIncidenceUseCase
import com.example.productmanager.domain.model.entities.Incidence
import com.example.productmanager.domain.model.entities.IncidenceStatus
import com.example.productmanager.domain.user_usescases.AddIncidentUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class IncidencesServiceTest() {


    @MockK
    private lateinit var addIncidenceUseCase: AddIncidentUseCase

    @MockK
    private lateinit var getAllIncidencesUseCase: GetIncidencesUseCase

    @MockK
    private lateinit var updateIncidenceUseCase: UpdateIncidenceUseCase

    @InjectMockKs
    private lateinit var incidencesService: IncidencesService


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenSaveIncidences_thenGetIncidences() = runBlocking {
        val incidence = Incidence(
            id = "0",
            employee = "employee",
            date = "21/12/23",
            id_tool = "00001",
            tool_name = "tool",
            description = "description",
            status = IncidenceStatus.PENDING.toString()
        )
        coEvery { addIncidenceUseCase(incidence) } returns true

        val result = incidencesService.saveIncidence(incidence)

        coVerify { addIncidenceUseCase(incidence) }

        assertEquals(true, result)
    }

    @Test
    fun whenSolveIncidence_thenGetSolvedIncidence() = runBlocking {
        val incidence = Incidence(
            id = "0",
            employee = "employee",
            date = "21/12/23",
            id_tool = "00001",
            tool_name = "tool",
            description = "description",
            status = IncidenceStatus.PENDING.toString()
        )

        coEvery { updateIncidenceUseCase(incidence) } returns true

        val result = incidencesService.solveIncidence(incidence)

        coVerify { updateIncidenceUseCase(incidence) }

        assertEquals(true, result)
    }


}