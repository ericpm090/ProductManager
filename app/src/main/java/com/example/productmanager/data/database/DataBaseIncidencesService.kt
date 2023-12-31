package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.entities.Incidence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DataBaseIncidencesService @Inject constructor(private val database: FirebaseFirestore){

    companion object {
        val TAG_DATABASE = "TAG_DATABASE_INCIDENCES"
        val COLLECTION = "incidences"
    }


    suspend fun save(incidence: Incidence): Boolean {
        return suspendCoroutine { continuation ->
            try {
                val doc = database.collection(COLLECTION).document()
                doc.set(
                    hashMapOf(
                        "id" to incidence.id,
                        "employee" to incidence.employee,
                        "date" to incidence.date,
                        "id_tool" to incidence.id_tool,
                        "tool_name" to incidence.tool_name,
                        "description" to incidence.description,
                        "status" to incidence.status
                    )
                ).addOnSuccessListener {
                    doc.update("id", doc.id)
                    Log.i(TAG_DATABASE, "Incidence ${doc.id} added in database ")
                    continuation.resume(true)
                }.addOnFailureListener {
                    Log.w(TAG_DATABASE, "Error writing document", it)
                    continuation.resume(false)
                }
            } catch (e: Exception) {
                Log.w(TAG_DATABASE, "Error writing document", e)
                continuation.resume(false)
            }
        }
    }
    suspend fun getAll(): MutableList<Incidence> {
        val list = mutableListOf<Incidence>()

        val colection = withContext(Dispatchers.IO) {
            database.collection(COLLECTION).get().await()
        }
        if (!colection.isEmpty) {
            colection.documents.forEach { document ->
                list.add(
                    Incidence(
                        document.get("id") as String,
                        document.get("employee") as String,
                        document.get("date") as String,
                        document.get("id_tool") as String,
                        document.get("tool_name") as String,
                        document.get("description") as String,
                        document.get("status") as String


                    )


                )
            }
        }
        return list
    }

    fun size(): Int {
        Log.i(TAG_DATABASE, "Not yet implemented")
        return 0
    }


    suspend fun update(incidence: Incidence): Boolean {
        var result = false
        val doc = database.collection(COLLECTION).document(incidence.id)
        doc.set(
            hashMapOf(
                "id" to incidence.id,
                "employee" to incidence.employee,
                "date" to incidence.date,
                "id_tool" to incidence.id_tool,
                "tool_name" to incidence.tool_name,
                "description" to incidence.description,
                "status" to incidence.status

            )

        ).addOnSuccessListener {
            result = true
        }
            .addOnFailureListener { e ->
                Log.w(
                    TAG_DATABASE,
                    "Error writing document", e
                )
            }.await()

        return result
    }








}