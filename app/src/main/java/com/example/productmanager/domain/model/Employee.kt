package com.example.productmanager.domain.model

import javax.inject.Inject

data class Employee @Inject constructor(val name:String, val email:String, val password:String) {


}