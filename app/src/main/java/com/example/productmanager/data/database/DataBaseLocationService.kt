package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.entities.Location
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseLocationService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
        val COLLECTION = "locations"
    }


    suspend fun save(name: String): Boolean {
        var result = false
        val code = getCounterLocations() +1
        database.collection(COLLECTION).document(name).set(
            hashMapOf(
                "code" to code,
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
            }.await()

        return result

    }


    suspend fun get(name: String): Location? {
        var location: Location? = null
        val doc = withContext(Dispatchers.IO){
            database.collection(COLLECTION).document(name).get().await()
        }
        if (doc.exists()) {
            location = Location(
                code = doc.get("code") as Long,
                name = doc.get("name") as String
            )
        } else {
            Log.e(TAG_DATABASE, "Error: Document not exist")

        }

        return location
    }

    fun delete(name: String): Boolean {
        database.collection(COLLECTION).document(name).delete()
        return true
    }




    suspend fun getAllLocations(): MutableList<String> {

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
            Log.d(DataBaseProjectService.TAG_DATABASE, "Error getting documents: ", e)
        }

        return list

    }

    suspend fun getCounterLocations(): Int {
        var count = 0
        try{
            database.collection(COLLECTION).count().get(AggregateSource.SERVER).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    // Count fetched successfully
                    count = task.result.count.toInt()
                    Log.d(TAG_DATABASE, "Current number locations: ${count}")
                } else {
                    Log.d(TAG_DATABASE, "Count failed: ", task.getException())
                }

            }.await()


        }catch (e:Exception){
            Log.w(TAG_DATABASE,"Warning: Collection not found, creating ${DataBaseProjectService.COLLECTION} database")
        }

        return count
    }
}