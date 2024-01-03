package com.example.productmanager.ui.admin.ui.toolHistory_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.domain.model.services.RentalToolService
import com.example.productmanager.domain.model.services.ToolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryToolViewModel @Inject constructor(private val rentalToolService: RentalToolService
): ViewModel() {

    val historyList = MutableLiveData<MutableList<RentalTool>>()
    fun onGetHistoryToolSelected(barcode:String){
        viewModelScope.launch {

            withContext(Dispatchers.IO){
                historyList.postValue(rentalToolService.getRentalToolHistory(barcode))
            }
        }
    }

}