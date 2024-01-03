package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.entities.Employee
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseUserService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DB_USER_SERVICE"
        val USERS_COLLECTION = "users"
    }

    fun save(employee: Employee): Boolean {
        var result = false
        database.collection(USERS_COLLECTION).document(employee.email).set(
            hashMapOf(
                "email" to employee.email,
                "name" to employee.name,
                "password" to employee.password
            ),
        ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "User ${employee.email} added in database ")
            result = true
        }.addOnFailureListener { e -> Log.w(TAG_DATABASE, "Error adding document", e) }
        return result
    }

    fun save(email: String) {

        database.collection(USERS_COLLECTION).document(email).set(
            hashMapOf(
                "email" to email,
                "name" to "Google User",
                "password" to "Google User"
            ),

            ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "User $email added in database ")
        }
            .addOnFailureListener { e -> Log.w(TAG_DATABASE, "Error writing document", e) }

    }


    suspend fun getUser(email: String): Employee? {
        var user: Employee? = null
        val doc = withContext(Dispatchers.IO) {
            database.collection(USERS_COLLECTION).document(email).get().await()
        }
        if (doc.exists()) {

            user = Employee(
                name = doc.get("name") as String,
                email = email,
                password = doc.get("password") as String
            )
        } else {
            Log.e(TAG_DATABASE, "Error: Document not exist")

        }

        return user
    }

    fun deleteUser(email: String): Boolean {
        database.collection(USERS_COLLECTION).document(email).delete()
        return true
    }

    suspend fun getAll(): MutableList<Employee> {

        val usersList = mutableListOf<Employee>()
        val collection = withContext(Dispatchers.IO) {
            database.collection(USERS_COLLECTION)
                .get().await()
        }
        if (!collection.isEmpty) {

            for (document in collection.documents) {
                usersList.add(
                    Employee(
                        name = document.get("name") as String,
                        email = document.get("email") as String,
                        password = document.get("password") as String
                    )
                )

            }


        } else {
            Log.e(DataBaseToolService.TAG_DATABASE, "Error: Document not exist")

        }

        return usersList
    }


}