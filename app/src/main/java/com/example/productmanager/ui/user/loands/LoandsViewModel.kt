package com.example.productmanager.ui.user.loands

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
class LoandsViewModel @Inject constructor(
    private val rentalToolService: RentalToolService

) : ViewModel() {


    val update = MutableLiveData<MutableList<RentalTool>?>()

    /*
    Call getAllPendingTools to obtain pending tools.
     */
    fun updateLoands(email: String) {
        viewModelScope.launch {

            update.postValue(withContext(Dispatchers.IO) {
                rentalToolService.getAllPendingTools(email)})
        }

    }
}