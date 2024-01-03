package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.entities.Tool
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseToolService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_TOOLS_DATABASE"
        val COLLECTION = "tools"
    }

    suspend fun save(
       tool: Tool
    ): String {
        var result = ""

        try{

            database.collection(COLLECTION).document(tool.barcode).set(
                hashMapOf(
                    "barcode" to tool.barcode,
                    "name" to tool.name,
                    "project" to tool.project,
                    "location" to tool.location,
                    "photo" to tool.photo,
                    "type" to tool.type,
                    "status" to tool.status
                )

            ).addOnSuccessListener {
                result = tool.barcode
                val num = database.collection(COLLECTION).count().get(AggregateSource.SERVER)
                Log.i(TAG_DATABASE, "Tool $tool.name with barcode $tool.barcode added in database")
                Log.i(TAG_DATABASE, "Total tools saved: $num")
            }.await()


        }catch (e:Exception){
            Log.w(TAG_DATABASE,"Error writing document", e)
        }
        return result

    }
    suspend fun get(barcode: String): Tool? {
        var tool: Tool? = null
        val doc = withContext(Dispatchers.IO) {
            database.collection(COLLECTION).document(barcode).get().await()
        }
        
        if (doc.exists()) {

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

        return tool
    }

    fun delete(name: String): Boolean {
        database.collection(COLLECTION).document(name).delete()
        return true
    }

    suspend fun getCounterTools(): Int {
        var count = 0
        try{
            database.collection(COLLECTION).count().get(AggregateSource.SERVER).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    // Count fetched successfully
                    count = task.result.count.toInt()
                    Log.i(TAG_DATABASE, "Current number tools: ${count}")
                } else {
                    Log.d(TAG_DATABASE, "Count failed: ", task.getException())
                }

            }.await()


        }catch (e:Exception){
            Log.w(TAG_DATABASE,"Warning: Collection not found, creating $COLLECTION database")
        }

        return count
    }
}