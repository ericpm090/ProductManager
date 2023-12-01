package com.example.productmanager.domain.model

import com.example.productmanager.domain.admin_usescases.GetLastToolUserCase
import com.example.productmanager.domain.admin_usescases.SearchLocationUseCase
import com.example.productmanager.domain.admin_usescases.SearchProjectUseCase
import javax.inject.Inject

class Barcode @Inject constructor(
    private val searchProjectUseCase: SearchProjectUseCase,
    private val searchLocationUseCase: SearchLocationUseCase,
    private val getLastToolUserCase: GetLastToolUserCase

){
    suspend operator fun invoke(type:String, project:String, location: String):String{
    //suspend fun generateToolBarcode(type:String, project:String, location: String):String{


        val ctype = ToolType.getCodeByType(type)
        val cproject = searchProjectUseCase(project)?.code.toString().padStart(2,'0')
        val clocation = searchLocationUseCase(location)?.code.toString().padStart(2,'0')
        val ctool =   getLastToolUserCase().toString().padStart(6,'0')
        return  ctype+cproject+clocation+ctool
    }




}
