package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.entities.RentalTool
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseRentalsService @Inject constructor(private val database: FirebaseFirestore) {
    companion object {
        val TAG_DATABASE = "TAG_RENTALS_DATABASE"
        val COLLECTION = "rentals"
        val COLLECTION2 = "records"
    }

    suspend fun save(
        rental_tool: RentalTool
    ): Boolean {
        var result = false

        try {


            database.collection(COLLECTION).document(rental_tool.userMail)
                .collection(COLLECTION2).document(rental_tool.barcode).set(
                    hashMapOf(
                        "barcode" to rental_tool.barcode,
                        "tool_name" to rental_tool.toolName,
                        "project" to rental_tool.project,
                        "location" to rental_tool.location,
                        "user_mail" to rental_tool.userMail,
                        "pickUpDate" to rental_tool.pickUpDate,
                        "dropOffDate" to rental_tool.dropOffDate,
                        "photo" to rental_tool.photo,
                        "status" to rental_tool.status
                    )

                ).addOnSuccessListener {
                    result = true
                    Log.i(TAG_DATABASE, "Rental has been successfully saved")
                }.await()

        } catch (e: Exception) {
            Log.w(DataBaseToolService.TAG_DATABASE, "Error writing document", e)
        }
        return result

    }

    suspend fun getAll(email: String): MutableList<RentalTool> {

        val rentalList = mutableListOf<RentalTool>()
        val collection = withContext(Dispatchers.IO) {
            database.collection(COLLECTION)
                .document(email)
                .collection(COLLECTION2)
                .get().await()
        }
        if (!collection.isEmpty) {

            for (document in collection.documents) {
                rentalList.add(
                    RentalTool(
                        barcode = document.get("barcode") as String,
                        userMail = document.get("user_mail") as String,
                        toolName = document.get("tool_name") as String,
                        project = document.get("project") as String,
                        location = document.get("location") as String,
                        pickUpDate = document.get("pickUpDate") as String,
                        dropOffDate = document.get("dropOffDate") as String,
                        status = document.get("status") as String,
                        photo = document.get("photo") as String
                    )
                )

            }


        } else {
            Log.e(DataBaseToolService.TAG_DATABASE, "Error: Document not exist")

        }

        return rentalList
    }

    suspend fun get(email: String, barcode: String): RentalTool? {

        var rentalTool: RentalTool? = null


        val document = withContext(Dispatchers.IO) {
            database.collection(COLLECTION).document(email)
                .collection(COLLECTION2).document(barcode).get().await()

        }
        if (document.exists()) {
            Log.i(TAG_DATABASE, "Document found! ")
            rentalTool = RentalTool(
                barcode = document.get("barcode") as String,
                userMail = document.get("user_mail") as String,
                toolName = document.get("tool_name") as String,
                project = document.get("project") as String,
                location = document.get("location") as String,
                pickUpDate = document.get("pickUpDate") as String,
                dropOffDate = document.get("dropOffDate") as String,
                status = document.get("status") as String,
                photo = document.get("photo") as String


            )
            Log.i(TAG_DATABASE, "Pending tool found! ")

        } else {
            Log.e(TAG_DATABASE, "Error: Document not exist")

        }

        return rentalTool
    }


}