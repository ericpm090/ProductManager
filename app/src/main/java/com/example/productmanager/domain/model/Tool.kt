package com.example.productmanager.domain.model

import javax.inject.Inject

data class Tool @Inject constructor(var name:String, var project:String, var description:String) {
}