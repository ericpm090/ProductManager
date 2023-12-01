package com.example.productmanager.domain.admin_usescases

import com.example.productmanager.data.database.DataBaseToolService
import javax.inject.Inject

class GetLastToolUserCase @Inject constructor(private val db:DataBaseToolService){

    operator fun invoke() : Int {

        return db.getCounterTools()
    }
}