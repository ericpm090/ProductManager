package com.example.productmanager.ui.user.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.domain.model.services.ToolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toolService: ToolService
) : ViewModel() {
    var email = ""
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


}