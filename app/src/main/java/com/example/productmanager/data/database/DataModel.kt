package com.example.productmanager.data.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FirebaseModel {
    @Singleton //mantiene una unica instancia de Firebase
    @Provides
    fun provideFireBase(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun getCurrentUser(firebaseAuth: FirebaseAuth): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    @Singleton //mantiene una unica instancia de Firebase
    @Provides
    fun provideFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()

    }


}