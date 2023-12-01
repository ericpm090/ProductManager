package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.Tool
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataBaseToolService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DATABASE"
        val COLLECTION = "tools"
    }




    suspend fun save(
        barcode: String,
        name: String,
        project: String,
        location:String,
        photo: String,
        type: String,
        status: String
    ): Boolean {
        var result = false

        try{

            database.collection(COLLECTION).document(barcode).set(
                hashMapOf(
                    "barcode" to barcode,
                    "name" to name,
                    "project" to project,
                    "location" to location,
                    "photo" to photo,
                    "type" to type,
                    "status" to status
                )

            ).addOnSuccessListener {
                result = true
                val num = database.collection(COLLECTION).count().get(AggregateSource.SERVER)
                Log.i(TAG_DATABASE, "Tool $name with barcode $barcode added in database")
                Log.i(TAG_DATABASE, "Total tools saved: $num")
            }.await()


        }catch (e:Exception){
            Log.w(TAG_DATABASE,"Error writing document", e)
        }
        return result

    }
    suspend fun get(barcode: String): Tool? {
        var tool: Tool? = null
        database.collection(COLLECTION).document(barcode).get()
            .addOnSuccessListener { doc ->
                if (doc != null) {

                    tool = Tool(
                        barcode = doc.get("barcode") as String,
                        name = doc.get("name") as String,
                        project = doc.get("project") as String,
                        location = doc.get("location") as String,
                        photo = doc.get("photo") as String,
                        type = doc.get("project") as String,
                        status = doc.get("status") as String
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

    fun getCounterTools(): Int {
        var count = 0
        try{
            database.collection(COLLECTION).count().get(AggregateSource.SERVER).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    // Count fetched successfully
                    count = task.result.count.toInt()
                    Log.d(TAG_DATABASE, "Current number tools: ${count}")
                } else {
                    Log.d(TAG_DATABASE, "Count failed: ", task.getException())
                }

            }


        }catch (e:Exception){
            Log.w(TAG_DATABASE,"Warning: Collection not found, creating $COLLECTION database")
        }

        return count
    }
}