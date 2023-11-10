package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.Employee
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
    }

    fun saveUser(email: String, name: String, password: String): Boolean {
        var result = false
        database.collection("users").document(email).set(
            hashMapOf(
                "email" to email,
                "name" to name,
                "password" to password
            ),
        ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "User $email added in database ")
            result = true
        }.addOnFailureListener { e -> Log.w(TAG_DATABASE, "Error adding document", e) }
        return result
    }

    fun saveUser(email: String) {

        database.collection("users").document(email).set(
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


    fun getUser(email: String): Employee? {
        val user: Employee? = null
        database.collection("users").document(email).get().addOnSuccessListener {
            user!!.name = (it.get("name") as String?).toString()
            user!!.password = (it.get("password") as String?).toString()

        }
        return user
    }

    fun deleteUser(email: String): Boolean {
        database.collection("users").document(email).delete()
        return true
    }
}