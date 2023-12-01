package com.example.productmanager.data.database

import android.util.Log
import com.example.productmanager.domain.model.Employee
import com.example.productmanager.domain.model.RentalTool
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseUserService @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        val TAG_DATABASE = "TAG_DB_USER_SERVICE"
        val USERS_COLLECTION = "users"
        val RENTALS_COLLECTION = "rentals"
    }

    fun save(email: String, name: String, password: String): Boolean {
        var result = false
        database.collection(USERS_COLLECTION).document(email).set(
            hashMapOf(
                "email" to email,
                "name" to name,
                "password" to password
            ),
        ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "User $email added in database ")
            result = true
        }.addOnFailureListener { e -> Log.w(TAG_DATABASE, "Error adding document", e) }
        return result
    }

    fun save(email: String) {

        database.collection(USERS_COLLECTION).document(email).set(
            hashMapOf(
                "email" to email,
                "name" to "Google User",
                "password" to "Google User"
            ),

            ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "User $email added in database ")
        }
            .addOnFailureListener { e -> Log.w(TAG_DATABASE, "Error writing document", e) }

    }


    suspend fun getUser(email: String): Employee? {
        var user:Employee? = null
        database.collection(USERS_COLLECTION).document(email).get().addOnSuccessListener { doc ->
            if (doc!=null){
                user = Employee(
                    name = doc.get("name") as String,
                    email = email,
                    password = doc.get("password") as String
                )
            }else{
                Log.e(TAG_DATABASE, "Error: Document not exist")

            }

        }.addOnFailureListener { exception ->
            Log.e(TAG_DATABASE, "Error: get failed with ", exception)
        }.await()
       return user
    }

    fun deleteUser(email: String): Boolean {
        database.collection(USERS_COLLECTION).document(email).delete()
        return true
    }

    fun getPendingTool(email:String, barcode:String): RentalTool? {
        var rentalTool: RentalTool? = null
        try {
            database.collection(RENTALS_COLLECTION).document(email).get().addOnSuccessListener { doc ->
                if(doc.get("tool_barcode").toString() == barcode){
                    rentalTool = RentalTool(
                        toolBarcode = doc.get("tool_barcode") as String,
                        toolName = doc.get("tool_name") as String,
                        toolProject = doc.get("tool_project") as String,
                        startRental = doc.get("start_rental") as String,
                        endRental = doc.get("end_rental") as String?
                    )
                    Log.i(TAG_DATABASE, "Pending tool ${barcode} find it")
                }

            }
        } catch (e: Exception) {
            Log.d(DataBaseProjectService.TAG_DATABASE, "Error getting documents: ", e)
        }

        return rentalTool

    }

    fun addPendingTool(email:String, rentalTool:RentalTool){
        database.collection(RENTALS_COLLECTION).document(email).set(
            hashMapOf(
                "tool_barcode" to email,
                "tool_name" to rentalTool.toolName,
                "tool_project" to rentalTool.toolProject,
                "start_rental" to rentalTool.startRental,
                "end_rental" to rentalTool.endRental

            ),

            ).addOnSuccessListener {
            Log.i(TAG_DATABASE, "Rental of $email has been registered successfully ")
        }
            .addOnFailureListener { e -> Log.w(TAG_DATABASE, "Error writing document", e) }
    }


}