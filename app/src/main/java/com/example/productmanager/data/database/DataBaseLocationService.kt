package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.Location
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataBaseLocationService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
        val COLLECTION = "locations"
    }


    fun save(name: String): Boolean {
        var result = false
        database.collection(COLLECTION).document(name).set(
            hashMapOf(
                "name" to name
            ),

            ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "Location $name added in database ")
            result = true
        }
            .addOnFailureListener { e ->
                Log.w(
                    TAG_DATABASE,
                    "Error writing document", e
                )
            }

        return result

    }


    suspend fun get(name: String): Location? {
        var location: Location? = null
        database.collection(COLLECTION).document(name).get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                    location = Location(
                        name = doc.get("name") as String
                    )
                } else {
                    Log.e(TAG_DATABASE, "Error: Document not exist")

                }

            }.addOnFailureListener { exception ->
                Log.e(TAG_DATABASE, "Error: get failed with ", exception)
            }.await()
        return location
    }

    fun delete(name: String): Boolean {
        database.collection(COLLECTION).document(name).delete()
        return true
    }


}