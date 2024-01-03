package com.example.productmanager.ui.user.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.domain.model.services.IncidencesService
import com.example.productmanager.domain.model.services.RentalToolService
import com.example.productmanager.domain.model.services.ToolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toolService: ToolService,
    private val rentalToolService: RentalToolService,
    private val incidencesService: IncidencesService
) : ViewModel() {
    var email = ""
    val pendingTools = MutableLiveData<Int?>()
    val totalRentalTools = MutableLiveData<Int?>()
    val totalIncidences = MutableLiveData<Int?>()
    val searchTool = MutableLiveData<Tool?>()

    fun onBarcodeRead(barcode: String) {
        if (barcode.isNotEmpty()) {
            viewModelScope.launch {
                searchTool.postValue(withContext(Dispatchers.IO) {
                    toolService.getToolbyBarcode(
                        barcode
                    )
                })
            }
        } else {
            searchTool.postValue(null)
        }

    }


    fun getPendingTools(email:String){
        viewModelScope.launch {
            pendingTools.postValue(withContext(Dispatchers.IO) {
                rentalToolService.getAllPendingTools(email).size
            })
            getRentalHistory(email)
        }

    }
    fun getRentalHistory(email:String){
        viewModelScope.launch {
            totalRentalTools.postValue(withContext(Dispatchers.IO) {
                rentalToolService.getRentalHistory(email).size
            })
            getIncidencesCreated(email)
        }
    }

    fun getIncidencesCreated(email:String){
        viewModelScope.launch {
            totalIncidences.postValue(withContext(Dispatchers.IO) {
                incidencesService.getIncidencesByEmail(email).size
            })
        }
    }


}