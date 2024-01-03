package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.entities.Project
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseProjectService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
        val COLLECTION = "projects"
    }


    suspend fun save(name: String): Boolean {
        var result = false
        val code = getCounterProjects() + 1

        try{
            database.collection(COLLECTION).document(name).set(
                hashMapOf(
                    "code" to code,
                    "name" to name
                )).addOnSuccessListener {
                    result = true
            }.await()
        }catch(e:Exception){
            Log.w(DataBaseToolService.TAG_DATABASE,"Error writing document", e)
        }







        /*database.collection(COLLECTION).document(name).set(
            hashMapOf(
                "code" to code,
                "name" to name
            ),

            ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "Project $name added in database ")
            Log.i(TAG_DATABASE, "Code of project $code added in database ")
            result = true
        }
            .addOnFailureListener { e ->
                Log.w(
                    TAG_DATABASE,
                    "Error writing document",
                    e
                )
            }.await()*/
        return result

    }

    suspend fun getAllProjects(): MutableList<String> {
        val list: MutableList<String> = mutableListOf()

        val query = withContext(Dispatchers.IO){
            database.collection(COLLECTION).get().await()
        }
        try {
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


    suspend fun get(name: String): Project? {
        var project: Project? = null

        val doc = withContext(Dispatchers.IO){
            database.collection(COLLECTION).document(name).get().await()
        }
        if (doc.exists()) {
            project = Project(
                code = doc.get("code") as Long,
                name = doc.get("name") as String
            )
        } else {
            Log.e(TAG_DATABASE, "Error: Document not exist")

        }

        return project
    }

    suspend fun delete(name: String): Boolean {
        withContext(Dispatchers.IO){
            database.collection(COLLECTION).document(name).delete().await()
        }
        return true
    }

    suspend fun getCounterProjects(): Int {
        var count = 0

        try {
            database.collection(COLLECTION).count().get(AggregateSource.SERVER)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Count fetched successfully
                        count = task.result.count.toInt()
                        Log.d(TAG_DATABASE, "Current number projects: ${count}")
                    } else {
                        Log.d(TAG_DATABASE, "Count failed: ", task.getException())
                    }

                }.await()


        } catch (e: Exception) {
            Log.w(
                DataBaseToolService.TAG_DATABASE,
                "Warning: Collection not found, creating ${COLLECTION} database"
            )
        }

        return count
    }
}