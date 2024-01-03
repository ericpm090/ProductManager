package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.GetAllUsersUseCase
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.domain.model.entities.RentalToolStatus
import com.example.productmanager.domain.user_usescases.AddRentalToolUseCase
import com.example.productmanager.domain.user_usescases.GetPendingToolUseCase
import com.example.productmanager.domain.user_usescases.GetRentalHistoryUseCase
import javax.inject.Inject

class RentalToolService @Inject constructor(

    private val addRentalToolUseCase: AddRentalToolUseCase,
    private val getPendingToolUseCase: GetPendingToolUseCase,
    private val getRentalHistoryUseCase: GetRentalHistoryUseCase,
    private val toolService: ToolService,
    private val getAllUsersUseCase: GetAllUsersUseCase
) {


    /*
   Method to find pending tool of a user.
    */
    suspend fun findPendingTool(email: String, barcode: String): RentalTool? {
        return getPendingToolUseCase(email, barcode)

    }

    /*
    Method to delivery all the pendint tools.
    Change the status of pending tools to delivered.
     */
    suspend fun deliveryRentalTools(rentalTools: MutableList<RentalTool>) {
        rentalTools.forEach { rentalTool ->
            rentalTool.status = RentalToolStatus.DELIVERED.toString()
        }
        rentalTools.forEach { rentalTool -> saveRentalTool(rentalTool) }
        rentalTools.forEach { rentalTool -> toolService.changeStatus(rentalTool.barcode) }

    }

    /*
    Save rental tool method
     */
    suspend fun saveRentalTool(rentalTool: RentalTool): Boolean {
        return addRentalToolUseCase(rentalTool)
    }
    /*
        Method to get all pending tools of user
     */
    suspend fun getAllPendingTools(email: String): MutableList<RentalTool> {

        val list = getRentalHistoryUseCase(email)

        return list.filter { rentalTool -> rentalTool.status == RentalToolStatus.PENDING.toString() }
            .toMutableList()

    }

    /*
    Method to get all tools used by user.
     */
    suspend fun getRentalHistory(email: String): MutableList<RentalTool> {
        return getRentalHistoryUseCase(email)
    }

    /*
    method to get the history of tool.
     */
    suspend fun getRentalToolHistory(barcode: String): MutableList<RentalTool> {
        val toolHistory = mutableListOf<RentalTool>()
        val usersList = getAllUsersUseCase()
        for (user in usersList) {

            toolHistory.addAll(
                getRentalHistory(user.email).filter { rentalTool ->
                    rentalTool.barcode.equals(
                        barcode
                    )
                }.toMutableList()
            )
        }
        return toolHistory
    }

}