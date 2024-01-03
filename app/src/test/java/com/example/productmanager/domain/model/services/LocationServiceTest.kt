package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.AddLocationUseCase
import com.example.productmanager.domain.admin_usescases.DeleteLocationUseCase
import com.example.productmanager.domain.admin_usescases.GetAllLocationsUseCase
import com.example.productmanager.domain.admin_usescases.SearchLocationUseCase
import com.example.productmanager.domain.model.entities.Location
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class LocationServiceTest(){

    @MockK
    private lateinit var getAllLocationsUseCase: GetAllLocationsUseCase

    @MockK
    private lateinit var addLocationUseCase: AddLocationUseCase

    @MockK
    private lateinit var searchLocationUseCase: SearchLocationUseCase

    @MockK
    private lateinit var deleteLocationUseCase: DeleteLocationUseCase

    @InjectMockKs
    private lateinit var locationService: LocationService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        locationService = LocationService(getAllLocationsUseCase,addLocationUseCase,searchLocationUseCase,deleteLocationUseCase)
    }

    @Test
    fun whenSearchLocations_thenGetAll()= runBlocking {
        val location1 = Location(
            1L, "0001"
        )
        val location2 = Location(
            2L, "0002"
        )
        val location3 = Location(
            3L, "0003"
        )
        val locations = mutableListOf(location1,location2,location3)

        coEvery { getAllLocationsUseCase() } returns locations

        val result = locationService.getLocations()
        coVerify(exactly = 1){getAllLocationsUseCase()  }

        Assert.assertEquals(locations.size, result.size)

    }


}