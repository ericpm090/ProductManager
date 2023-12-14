package com.example.productmanager.ui.admin.ui.tools

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.SearchToolUseCase
import com.example.productmanager.domain.admin_usescases.AddToolUseCase
import com.example.productmanager.domain.admin_usescases.DeleteToolUseCase
import com.example.productmanager.domain.admin_usescases.GetAllProjectsUseCase
import com.example.productmanager.domain.model.entities.Barcode
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.domain.model.entities.ToolStatus
import com.example.productmanager.domain.model.entities.ToolType
import com.example.productmanager.domain.model.services.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ToolsViewModel @Inject constructor(
    private val addTolCaseUse: AddToolUseCase,
    private val searchToolUseCase: SearchToolUseCase,
    private val deleteToolUseCase: DeleteToolUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val barcodeGenerator: Barcode,
    private val locationService: LocationService

) : ViewModel() {

    val addTool = MutableLiveData<Boolean?>()
    val searchTool = MutableLiveData<Tool?>()
    val deleteTool = MutableLiveData<Boolean>()
    var list: MutableList<String> = mutableListOf()
    val projectList = MutableLiveData<MutableList<String>>()
    val locationList = MutableLiveData<MutableList<String>>()
    val typeList = MutableLiveData<MutableList<String>>()


    private suspend fun getBarcode(type: String, project: String, location: String): String {
        return barcodeGenerator.getBarcode(type, project, location)

    }

    fun onAddToolSelected(
        name: String,
        project: String,
        location: String,
        type: String,
        photo: String,

        ) {
        val checkInput = name.isNotEmpty() && project.isNotEmpty()
                && location.isNotEmpty() && type.isNotEmpty() && photo.isNotEmpty()
        if (checkInput) {

            viewModelScope.launch {
                addTool.postValue(withContext(Dispatchers.IO) {

                    addTolCaseUse(
                        Tool(
                            getBarcode(type, project, location),
                            name,
                            project,
                            location,
                            photo,
                            type,
                            ToolStatus.AVAILABLE.toString()
                        )


                    )
                })
            }
        } else {
            addTool.postValue(false)
        }
    }


    fun onSearchToolSelected(barcode: String) {
        if (barcode.isNotEmpty()) {
            viewModelScope.launch {
                searchTool.postValue(withContext(Dispatchers.IO) { searchToolUseCase(barcode) })
            }
        } else {
            searchTool.postValue(null)
        }


    }

    fun onDeleteToolSelected(name: String) {
        if (name.isNotEmpty()) {
            deleteTool.postValue(deleteToolUseCase(name))
        } else {
            deleteTool.postValue(false)
        }

    }

    fun getProjects() {

        viewModelScope.launch {

            list = withContext(Dispatchers.IO) { getAllProjectsUseCase() }
            projectList.postValue(list)
        }


    }

    fun getLocations() {

        /*viewModelScope.launch {

            list = withContext(Dispatchers.IO) { getAllLocationsUseCase() }
            locationList.postValue(list)
        }*/
        viewModelScope.launch {

            list = withContext(Dispatchers.IO) { locationService.getLocations() }
            locationList.postValue(list)
        }


    }

    fun getTypes() {

        typeList.postValue(ToolType.getAllTypes())


    }


}