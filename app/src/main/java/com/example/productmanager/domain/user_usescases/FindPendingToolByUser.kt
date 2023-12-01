package com.example.productmanager.domain.user_usescases

import com.example.productmanager.data.database.DataBaseUserService
import com.example.productmanager.domain.model.RentalTool
import javax.inject.Inject

class FindPendingToolByUser @Inject constructor(private val dataBaseUserService: DataBaseUserService) {

    operator fun invoke(email: String, barcode:String): RentalTool? {

        return dataBaseUserService.getPendingTool(email, barcode)
    }
}