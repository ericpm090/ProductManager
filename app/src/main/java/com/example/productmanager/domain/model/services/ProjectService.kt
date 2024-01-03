package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.AddProjectUseCase
import com.example.productmanager.domain.admin_usescases.DeleteProjectUseCase
import com.example.productmanager.domain.admin_usescases.SearchProjectUseCase
import com.example.productmanager.domain.model.entities.Project
import javax.inject.Inject

class ProjectService @Inject constructor(
    private val addProjectUseCase: AddProjectUseCase,
    private val searchProjectUseCase: SearchProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase
) {

    /*
    Method to save project
     */
    suspend fun save(name: String): Boolean {
        return addProjectUseCase(name)
    }
    /*
       Method to get project
        */
    suspend fun get(name: String): Project? {
        return searchProjectUseCase(name)
    }

    /*
   Method to delete project
    */
    suspend fun delete(name: String): Boolean {
        return deleteProjectUseCase(name)
    }
}