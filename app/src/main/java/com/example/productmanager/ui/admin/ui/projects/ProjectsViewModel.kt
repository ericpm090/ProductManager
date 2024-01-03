package com.example.productmanager.ui.admin.ui.projects

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.Project
import com.example.productmanager.domain.model.services.ProjectService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val projectService: ProjectService
) : ViewModel() {

    val addProject = MutableLiveData<Boolean?>()
    val findProject = MutableLiveData<Project?>()
    val deleteProject = MutableLiveData<Boolean?>()

    /*
    If name is not emtpy, call to method save to save project. Post result
    Else post false.
  */
    fun onAddProjectSelected(name: String) {

        if (name.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    addProject.postValue(withContext(Dispatchers.IO) {
                        projectService.save(name)
                    })
                } catch (e: Exception) {
                    Log.w("ProjectsViewModel", "Error ", e)
                }


            }
        } else addProject.postValue(false)
    }
    /*
      If name is not emtpy, call to method get to search project. Post result
      Else post null.
    */
    fun onSearchProjectSelected(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                findProject.postValue(withContext(Dispatchers.IO) { projectService.get(name) })
            }
        } else {
            findProject.postValue(null)
        }
    }
    /*
      If name is not emtpy, call to method delete to delete project. Post result
      Else post false.
    */
    fun onDeleteProjectSelected(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                deleteProject.postValue(withContext(Dispatchers.IO) { projectService.delete(name) })
            }
        } else deleteProject.postValue(false)
    }


}