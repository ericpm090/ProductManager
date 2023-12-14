package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.entities.Incidence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseIncidencesService @Inject constructor(private val database: FirebaseFirestore) :
    DataBaseService<Incidence> {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE_INCIDENCES"
        val COLLECTION = "incidences"
    }

    override suspend fun save(incidence: Incidence): Boolean {
        var result = false
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
            Log.i(TAG_DATABASE, "Incidence ${incidence.id} added in database ")
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

    suspend override fun get(id: String): Incidence? {
        Log.i(TAG_DATABASE, "Not yet implemented")
        return null
    }

    suspend override fun getAll(): MutableList<Incidence> {
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

    suspend override fun size(): Int {
        Log.i(TAG_DATABASE, "Not yet implemented")
        return 0
    }


}