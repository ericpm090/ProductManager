package com.example.productmanager.ui.user.returns

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productmanager.domain.model.RentalTool
import com.example.productmanager.domain.user_usescases.FindPendingToolByUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReturnsViewModel @Inject constructor(
    private var getPendingToolUseCase: FindPendingToolByUser
) : ViewModel() {
    val searchPendingTool = MutableLiveData<RentalTool?>()
    var email = ""
    fun onBarcodeRead(barcode: String) {

        if (barcode.isNotEmpty() && email != "") {
            searchPendingTool.postValue(getPendingToolUseCase(email, barcode))
        } else {
            searchPendingTool.postValue(null)
        }

    }


}