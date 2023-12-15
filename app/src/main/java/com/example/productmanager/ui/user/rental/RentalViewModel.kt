package com.example.productmanager.ui.user.rental

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.domain.model.entities.RentalToolStatus
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.domain.model.services.LocationService
import com.example.productmanager.domain.model.services.RentalToolService
import com.example.productmanager.domain.model.services.ToolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor(
    private val toolService: ToolService,
    private val locationService: LocationService,
    private val rentalToolService: RentalToolService
) : ViewModel() {
    var email = ""
    var rentalList = mutableListOf<Tool>()

    val searchTool = MutableLiveData<Tool?>()
    val addRentalTool = MutableLiveData<Boolean?>()
    var list: MutableList<String> = mutableListOf()
    val locationList = MutableLiveData<MutableList<String>>()
    fun onSearchSelected(barcode: String) {

        viewModelScope.launch {

            val tool = withContext(Dispatchers.IO) {
                toolService.getToolbyBarcode(barcode)
            }
            if (tool != null) {
                toolService.changeStatus(tool.barcode)

                if (toolService.isAvailable(tool)) searchTool.postValue(tool)

            } else searchTool.postValue(null)
        }

    }


    fun onRegisterSelected(email: String, location: String) {
        rentalList.forEach { tool ->
            viewModelScope.launch {

                withContext(Dispatchers.IO) {

                    rentalToolService.saveRentalTool(
                        RentalTool(
                            email,
                            tool.barcode,
                            tool.name,
                            tool.project,
                            location,
                            LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                .toString(),
                            "",
                            RentalToolStatus.PENDING.toString(),
                            tool.photo
                        )
                    )
                    toolService.changeStatus(tool.barcode)
                }
                addRentalTool.postValue(true)
            }

        }
    }


    fun getLocations() {
        viewModelScope.launch {

            list = withContext(Dispatchers.IO) { locationService.getLocations() }
            locationList.postValue(list)
        }
    }


}



