package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.Employee
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseUserService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
        val COLLECTION = "users"
    }

    fun save(email: String, name: String, password: String): Boolean {
        var result = false
        database.collection(COLLECTION).document(email).set(
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

    fun save(email: String) {

        database.collection(COLLECTION).document(email).set(
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
        var user:Employee? = null
        database.collection(COLLECTION).document(email).get().addOnSuccessListener { doc ->
            if (doc!=null){
                user = Employee(
                    name = doc.get("name") as String,
                    email = email,
                    password = doc.get("password") as String
                )
            }else{
                Log.e(TAG_DATABASE, "Error: Document not exist")

            }

        }.addOnFailureListener { exception ->
            Log.e(TAG_DATABASE, "Error: get failed with ", exception)
        }.await()
       return user
    }

    fun deleteUser(email: String): Boolean {
        database.collection(COLLECTION).document(email).delete()
        return true
    }


}