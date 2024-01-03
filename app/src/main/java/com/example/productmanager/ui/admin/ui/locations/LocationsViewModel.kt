package com.example.productmanager.ui.admin.ui.locations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.Location
import com.example.productmanager.domain.model.services.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationService: LocationService
) : ViewModel() {

    val addLocation = MutableLiveData<Boolean?>()
    val findLocation = MutableLiveData<Location?>()
    val deleteLocation = MutableLiveData<Boolean>()
    /*
      If name is not emtpy, call to method createLocation to create location. Post result
      Else post false.
    */
    fun onAddLocationSelected(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                val location = locationService.createLocation(name)
                if (location != null) {
                    addLocation.postValue(withContext(Dispatchers.IO) {
                        //addLocationUseCase(location)
                        locationService.saveLocation(location)
                    })
                }else{
                    addLocation.postValue(false)
                }

            }
        } else addLocation.postValue(false)
    }

    /*
      If name is not emtpy, call to method searchLocation to get location. Post result
      Else post null.
    */
    fun onSearchLocationSelected(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                val location = locationService.searchLocation(name)
                if(location!=null){
                    findLocation.postValue(withContext(Dispatchers.IO) {location})
                }else{
                    findLocation.postValue(null)
                }
            }
        } else {
            findLocation.postValue(null)
        }
    }

    /*
      If name is not emtpy, call to method deleteLocation to delete location. Post result
      Else post false.
    */
    fun onDeleteLocationSelected(name: String) {
        if (name.isNotEmpty()) deleteLocation.postValue(locationService.deleteLocation(name))
        else deleteLocation.postValue(false)
    }
}