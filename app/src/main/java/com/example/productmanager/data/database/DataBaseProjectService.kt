package com.example.productmanager.data.database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataBaseProjectService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
        val COLLECTION = "projects"
    }


    fun save(name: String): Boolean {
        var result = false;
        database.collection(COLLECTION).document(name).set(
            hashMapOf(
                "name" to name
            ),

            ).addOnSuccessListener {
            Log.i(DataBaseUserService.TAG_DATABASE, "Project $name added in database ")
            result = true
        }
            .addOnFailureListener { e ->
                Log.w(
                    DataBaseUserService.TAG_DATABASE,
                    "Error writing document",
                    e
                )
            }
        return result

    }

    suspend fun getAllProjects(): MutableList<String> {
        val list: MutableList<String> = mutableListOf()
        try {
            val query = database.collection(COLLECTION).get().await()
            for (document in query.documents) {
                Log.d(TAG_DATABASE, "${document.id} => ${document.data}")
                list.add(
                    document.get("name") as String

                )
            }
        } catch (e: Exception) {
            Log.d(TAG_DATABASE, "Error getting documents: ", e)
        }

        return list
    }


    suspend fun get(name: String): String? {
        var project: String? = null
        database.collection(COLLECTION).document(name).get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                    project = doc.get("name") as String
                } else {
                    Log.e(TAG_DATABASE, "Error: Document not exist")

                }

            }.addOnFailureListener { exception ->
                Log.e(TAG_DATABASE, "Error: get failed with ", exception)
            }.await()
        return project
    }

    fun delete(name: String): Boolean {
        database.collection(COLLECTION).document(name).delete()
        return true
    }
}