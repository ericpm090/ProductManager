package com.example.productmanager.domain.model

import javax.inject.Inject

data class Project @Inject constructor(val code: Long, val name:String) {
}