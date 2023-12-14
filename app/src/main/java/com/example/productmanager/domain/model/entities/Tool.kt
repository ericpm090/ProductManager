package com.example.productmanager.domain.model.entities

import javax.inject.Inject

data class Tool @Inject constructor(
    var barcode: String,
    var name: String,
    var project: String,
    var location: String,
    var photo: String,
    var type: String,
    var status: String
) {
}