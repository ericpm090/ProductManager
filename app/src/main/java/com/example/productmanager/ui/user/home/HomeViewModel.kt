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

    /*
    If search buttom is pressed, call getToolbyBarcode method to get a tool. Post result.
    If barcode is emtpy post null.
     */
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

    /*
       If home is initialized , call getAllPendingTools to obtain the
       number pending tools. Post result.
        */
    fun getPendingTools(email:String){
        viewModelScope.launch {
            pendingTools.postValue(withContext(Dispatchers.IO) {
                rentalToolService.getAllPendingTools(email).size
            })
            getRentalHistory(email)
        }

    }

    /*
       If home is initialized , call getRentalHistory to obtain the
       number of tools used. Post result.

        */
    fun getRentalHistory(email:String){
        viewModelScope.launch {
            totalRentalTools.postValue(withContext(Dispatchers.IO) {
                rentalToolService.getRentalHistory(email).size
            })
            getIncidencesCreated(email)
        }
    }
    /*
          If home is initialized , call getIncidencesByEmail to obtain the
          number of incidences created by user. Post result.

           */
    fun getIncidencesCreated(email:String){
        viewModelScope.launch {
            totalIncidences.postValue(withContext(Dispatchers.IO) {
                incidencesService.getIncidencesByEmail(email).size
            })
        }
    }


}