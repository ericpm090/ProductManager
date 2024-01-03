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

    /*
   Create to get locations
    */
    suspend fun getLocations(): MutableList<String> {

        return getAllLocationsUseCase().map { location-> location.name }.toMutableList()

    }

    /*
   Create to create a location
    */
    fun createLocation(name:String): Location {
       return Location(
            code = 0L,
            name = name
        )
    }

    /*
   Create to save a location
    */
    suspend fun saveLocation(location:Location): Boolean {
        return addLocationUseCase(location)
    }

    /*
   Create to search a location
    */
    suspend fun searchLocation(name:String): Location? {
        return searchLocationUseCase(name)
    }

    /*
   Create to delete a location
    */
    fun deleteLocation(name:String): Boolean {
        return deleteLocationUseCase(name)
    }


}
