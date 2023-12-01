package com.example.productmanager.ui.user.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.SearchToolUseCase
import com.example.productmanager.domain.model.Tool
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchToolUseCase: SearchToolUseCase
) : ViewModel() {
    var email = ""
    var welcome_text = ""
    val searchTool = MutableLiveData<Tool?>()

    fun onBarcodeRead(barcode: String) {
        if (barcode.isNotEmpty()) {
            viewModelScope.launch {
                searchTool.postValue(withContext(Dispatchers.IO) { searchToolUseCase(barcode) })
            }
        } else {
            searchTool.postValue(null)
        }

    }


}