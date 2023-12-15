package com.example.productmanager.domain.model.entities

import javax.inject.Inject

data class Project @Inject constructor(val code: Long, val name:String) {
}