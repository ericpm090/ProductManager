package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.AddProjectUseCase
import com.example.productmanager.domain.admin_usescases.DeleteProjectUseCase
import com.example.productmanager.domain.admin_usescases.SearchProjectUseCase
import com.example.productmanager.domain.model.entities.Project
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ProjectServiceTest {
    @MockK
    private lateinit var addProjectUseCase: AddProjectUseCase

    @MockK
    private lateinit var searchProjectUseCase: SearchProjectUseCase

    @MockK
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase

    @InjectMockKs
    private lateinit var projectService: ProjectService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenSearchProject_thenReturnProject() = runBlocking {
        val project = Project(
            code = 1L,
            name = "Project"
        )
        coEvery { searchProjectUseCase(project.name) } returns project

        val result = projectService.get(project.name)

        coVerify(exactly = 1) { searchProjectUseCase(project.name) }

        assertEquals(project, result)

    }

    @Test
    fun whenSearchInvalidProject_thenReturnNull() = runBlocking {


        coEvery { searchProjectUseCase("") } returns null

        val result = projectService.get("")

        coVerify(exactly = 1) { searchProjectUseCase("") }

        assertEquals(null, result)
    }

    @Test
    fun whenSaveProject_thenReturnTrue() = runBlocking {
        coEvery { addProjectUseCase("Project") } returns true

        val result = projectService.save("Project")

        coVerify(exactly = 1) { addProjectUseCase("Project") }

        assertEquals(true, result)
    }

    @Test
    fun whenDeleteProject_thenReturnTrue() = runBlocking {
        coEvery { deleteProjectUseCase("Project") } returns true

        val result = projectService.delete("Project")

        coVerify(exactly = 1) { deleteProjectUseCase("Project") }

        assertEquals(true, result)
    }


}