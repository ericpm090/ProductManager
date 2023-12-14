package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.SearchToolUseCase
import com.example.productmanager.domain.admin_usescases.AddToolUseCase
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.domain.model.entities.ToolStatus
import javax.inject.Inject

class ToolService @Inject constructor(
    private val searchToolUsecase: SearchToolUseCase,
    private val addTolCaseUse: AddToolUseCase
) {

    suspend fun changeStatus(barcode: String): Boolean {
        val tool = getToolbyBarcode(barcode)
        var res = false
        if(tool!=null){
            if (tool.status.equals(ToolStatus.AVAILABLE.toString()) == true) tool.status =
                ToolStatus.NOT_AVAILABLE.toString()
            else tool.status = ToolStatus.AVAILABLE.toString()
            res = saveTool(tool)
        }

        return res


    }

    suspend fun getToolbyBarcode(barcode: String): Tool? {
        return searchToolUsecase(barcode)
    }

    suspend fun saveTool(tool: Tool): Boolean {

        return addTolCaseUse(tool)
    }

    fun isAvailable(tool: Tool?): Boolean {
        val res = tool?.status.equals(ToolStatus.AVAILABLE.toString())

        return res
    }

}

