package com.example.productmanager.domain.user_usescases

import com.example.productmanager.data.database.DataBaseRentalsService
import com.example.productmanager.domain.model.entities.RentalTool
import javax.inject.Inject

class GetRentalHistoryUseCase @Inject constructor(private val dataBaseRentalsService: DataBaseRentalsService) {

    suspend operator fun invoke(email: String): MutableList<RentalTool> {

        return dataBaseRentalsService.getAll(email)
    }
}