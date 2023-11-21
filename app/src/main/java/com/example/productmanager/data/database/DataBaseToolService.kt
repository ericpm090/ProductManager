package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.Tool
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataBaseToolService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
        val COLLECTION = "tools"
    }


    fun save(name: String, owner: String, description:String): Boolean {
        var result = false
        database.collection(COLLECTION).document(name).set(
            hashMapOf(
                "name" to name,
                "owner" to owner,
                "description" to description
            ),

            ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "Tool $name added in database ")
            result = true
        }
            .addOnFailureListener { e ->
                Log.w(TAG_DATABASE,
                    "Error writing document",
                    e
                )
            }
        return result

    }


    suspend fun get(name: String): Tool? {
        var tool: Tool? = null
        database.collection(COLLECTION).document(name).get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                    tool = Tool(
                        name = doc.get("name") as String,
                        project = doc.get("owner") as String,
                        description = doc.get("description") as String
                    )
                } else {
                    Log.e(TAG_DATABASE, "Error: Document not exist")

                }

            }.addOnFailureListener { exception ->
            Log.e(TAG_DATABASE, "Error: get failed with ", exception)
        }.await()
        return tool
    }

    fun delete(name: String): Boolean {
        database.collection(COLLECTION).document(name).delete()
        return true
    }
}