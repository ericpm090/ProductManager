package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.GetAllLocationsUseCase
import javax.inject.Inject

class LocationService @Inject constructor(
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) {

    suspend fun getLocations(): MutableList<String> {

        return getAllLocationsUseCase()

    }


}
