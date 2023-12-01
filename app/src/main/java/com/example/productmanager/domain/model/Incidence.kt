package com.example.productmanager.domain.model

data class Incidence(
    val id: String,
    val employee: String,
    val date: String,
    val id_tool: String,
    val tool: String,
    val description: String
) {
}