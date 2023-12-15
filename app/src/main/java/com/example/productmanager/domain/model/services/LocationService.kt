package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.AddLocationUseCase
import com.example.productmanager.domain.admin_usescases.DeleteLocationUseCase
import com.example.productmanager.domain.admin_usescases.GetAllLocationsUseCase
import com.example.productmanager.domain.admin_usescases.SearchLocationUseCase
import com.example.productmanager.domain.model.entities.Location
import javax.inject.Inject

class LocationService @Inject constructor(
    private val getAllLocationsUseCase: GetAllLocationsUseCase,
    private val addLocationUseCase: AddLocationUseCase,
    private val searchLocationUseCase: SearchLocationUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase
) {

    suspend fun getLocations(): MutableList<String> {

        return getAllLocationsUseCase().map { location-> location.name }.toMutableList()

    }

    fun createLocation(name:String): Location {
       return Location(
            code = 0L,
            name = name
        )
    }

    suspend fun saveLocation(location:Location): Boolean {
        return addLocationUseCase(location)
    }

    suspend fun searchLocation(name:String): Location? {
        return searchLocationUseCase(name)
    }

    fun deleteLocation(name:String): Boolean {
        return deleteLocationUseCase(name)
    }


}
