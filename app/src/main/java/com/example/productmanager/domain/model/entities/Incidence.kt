package com.example.productmanager.domain.model.entities

data class Incidence(
    val id: String,
    val employee: String,
    val date: String,
    val id_tool: String,
    val tool_name:String,
    val description: String,
    var status:String
) {
}