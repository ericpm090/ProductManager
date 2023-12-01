package com.example.productmanager.ui.admin.ui.locations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.admin_usescases.AddLocationUseCase
import com.example.productmanager.domain.admin_usescases.DeleteLocationUseCase
import com.example.productmanager.domain.admin_usescases.SearchLocationUseCase
import com.example.productmanager.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val addLocationUseCase: AddLocationUseCase,
    private val searchLocationUseCase: SearchLocationUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase
) : ViewModel() {

    val addLocation = MutableLiveData<Boolean?>()
    val findLocation = MutableLiveData<Location?>()
    val deleteLocation = MutableLiveData<Boolean>()

    fun onAddLocationSelected(name: String) {
        if (name.isNotEmpty()){
            viewModelScope.launch {
                addLocation.postValue(withContext(Dispatchers.IO) {addLocationUseCase(name)})
            }
        }
        else addLocation.postValue(false)
    }

    fun onSearchLocationSelected(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                findLocation.postValue(withContext(Dispatchers.IO) { searchLocationUseCase(name) })
            }
        } else {
            findLocation.postValue(null)
        }
    }

    fun onDeleteLocationSelected(name: String) {
        if (name.isNotEmpty()) deleteLocation.postValue(deleteLocationUseCase(name))
        else deleteLocation.postValue(false)
    }
}