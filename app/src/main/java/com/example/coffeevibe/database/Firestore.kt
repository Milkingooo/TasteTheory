package com.example.coffeevibe.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Firestore {
    companion object{
        fun getInstance(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }
}