package com.example.productmanager.ui.user.returns

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.domain.model.services.RentalToolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReturnsViewModel @Inject constructor(
    private val rentalToolService: RentalToolService,

    ) : ViewModel() {
    val searchPendingTool = MutableLiveData<RentalTool?>()
    val registerRentalTools = MutableLiveData<Boolean>()

    //var email = ""
    var rentalList = mutableListOf<RentalTool>()

    fun onBarcodeRead(email: String, barcode: String) {

        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                rentalToolService.findPendingTool(email, barcode)
            }
            searchPendingTool.postValue(res)
        }
    }

    fun onRegisterSelected() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                rentalToolService.deliveryRentalTools(rentalList)

            }
            registerRentalTools.postValue(true)
        }
    }


}