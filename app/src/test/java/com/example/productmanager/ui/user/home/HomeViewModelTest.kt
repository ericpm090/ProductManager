package com.example.productmanager.ui.user.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productmanager.domain.model.services.IncidencesService
import com.example.productmanager.domain.model.services.RentalToolService
import com.example.productmanager.domain.model.services.ToolService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @MockK
    private lateinit var toolService: ToolService

    @MockK
    private lateinit var rentalToolService: RentalToolService

    @MockK
    private lateinit var incidencesService: IncidencesService

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(toolService, rentalToolService, incidencesService)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun whenBarcodeEmpty_thenReturnNull() = runTest {
        coEvery { toolService.getToolbyBarcode("") } returns null
        homeViewModel.onBarcodeRead("")
        assertEquals(null, homeViewModel.searchTool.value)
    }


}