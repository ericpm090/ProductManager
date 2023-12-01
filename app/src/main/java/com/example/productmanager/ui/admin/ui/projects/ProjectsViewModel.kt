package com.example.productmanager.ui.admin.ui.projects

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.admin_usescases.AddProjectUseCase
import com.example.productmanager.domain.admin_usescases.DeleteProjectUseCase
import com.example.productmanager.domain.admin_usescases.SearchProjectUseCase
import com.example.productmanager.domain.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val addProjectUseCase: AddProjectUseCase,
    private val searchProjectUseCase: SearchProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase
) : ViewModel() {

    val addProject = MutableLiveData<Boolean?>()
    val findProject = MutableLiveData<Project?>()
    val deleteProject = MutableLiveData<Boolean>()

    fun onAddProjectSelected(name: String) {

        if (name.isNotEmpty()){
            viewModelScope.launch {
                addProject.postValue(withContext(Dispatchers.IO){addProjectUseCase(name)})
            }
        }
        else addProject.postValue(false)
    }

    fun onSearchProjectSelected(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                findProject.postValue(withContext(Dispatchers.IO) { searchProjectUseCase(name) })
            }
        } else {
            findProject.postValue(null)
        }
    }

    fun onDeleteProjectSelected(name: String) {
        if (name.isNotEmpty()) deleteProject.postValue(deleteProjectUseCase(name))
        else deleteProject.postValue(false)
    }


}