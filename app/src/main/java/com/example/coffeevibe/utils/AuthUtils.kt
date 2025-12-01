package com.example.coffeevibe.utils

import com.google.firebase.auth.FirebaseAuth

object AuthUtils {
    private val auth = FirebaseAuth.getInstance()
    fun isUserAuth() : Boolean = auth.currentUser != null
    fun getUserId() = auth.currentUser?.uid
}