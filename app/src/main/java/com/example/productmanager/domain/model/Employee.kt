package com.example.productmanager.domain.model

import javax.inject.Inject

data class Employee @Inject constructor(var name:String, var email:String, var password:String) {


}