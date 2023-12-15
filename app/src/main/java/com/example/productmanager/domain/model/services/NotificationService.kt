package com.example.productmanager.domain.model.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import javax.inject.Inject

class NotificationService @Inject constructor() {

    fun notify(email: String, subject: String, message: String, context: Context) {
        val sender = "eperegrina@ifae.es"
        val uri = "mailto:$email" +
                "?subject=${Uri.encode(subject)}" +
                "&body=${Uri.encode(message)}" +
                "&from=${Uri.encode(sender)}"

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(uri)
        }

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Email client.."))
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }


    }

}

