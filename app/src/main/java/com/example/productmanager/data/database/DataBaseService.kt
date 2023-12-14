package com.example.productmanager.data.database

interface DataBaseService<T> {

    suspend fun save(incidence: T): Boolean
    suspend fun get(id: String): T?
    suspend fun getAll(): MutableList<T>
    suspend fun size(): Int

}