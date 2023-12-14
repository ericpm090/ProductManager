package com.example.productmanager.domain.model.entities

data class RentalTool(val userMail:String, val barcode:String, val toolName:String, val project:String, val location:String, val pickUpDate:String, val dropOffDate:String?, var status:String, val photo:String) {
}

