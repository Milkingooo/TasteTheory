package com.example.coffeevibe.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(val context: Context) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun login(login: String, password: String, isLogin: (Boolean) -> Unit) {
        try {
            if (login.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(login, password)
                    .addOnSuccessListener {
                        isLogin(true)
                        Toast.makeText(context, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        isLogin(false)
                        catchException(e)
                        Log.d("Login", e.message.toString())
                    }
            } else {
                isLogin(false)
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            isLogin(false)
            catchException(e)
        }
    }

    fun signUp(email: String, password: String, name: String, isSignUp: (Boolean) -> Unit) {
        try {
            if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && password.length >= 6) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        isSignUp(true)
                        addUserInDb(
                            id = auth.currentUser?.uid.toString(),
                            email = email,
                            password = password,
                            name = name
                        )
                        Toast.makeText(context, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        isSignUp(false)
                        catchException(e)
                    }
            } else isSignUp(false)
        } catch (e: Exception) {
            isSignUp(false)
            catchException(e)
        }
    }

    private fun addUserInDb(name: String, email: String, password: String, id: String) {
        viewModelScope.launch {
            db.collection("Client")
                .add(
                    hashMapOf(
                        "Name" to name,
                        "Email" to email,
                        "Password" to password,
                        "Id" to id
                    )
                )
                .addOnSuccessListener {
                    Log.d("UserAdd", "User added")
                }
                .addOnFailureListener {
                    Log.d("UserAdd", it.message.toString())
                }
        }
    }

    suspend fun checkEmailInDb(email: String): Boolean {
        return try {
            !(db.collection("Client").whereEqualTo("Email", email).get().await().isEmpty)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(context, "Письмо отправлено", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Письмо не отправлено", Toast.LENGTH_SHORT).show()
            }
    }

    fun logout() {
        if (auth.currentUser != null) Toast.makeText(
            context,
            "Вы вышли из аккаунта",
            Toast.LENGTH_SHORT
        ).show()
        auth.signOut()
    }

    fun giveUserName(getName: (String) -> Unit) {
        db.collection("Client")
            .whereEqualTo("Id", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    getName(document.getString("Name").toString())
                    Log.d("Name", document.getString("Name").toString())
                    break
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error getting user name", Toast.LENGTH_SHORT).show()
                Log.d("Name", "Error getting user name")
                getName("Нет имени :(")
            }
    }

    fun isLogin(): Boolean {
        return auth.currentUser != null
    }

    private fun catchException(e: Exception) {
        if (e is FirebaseAuthUserCollisionException) {
            when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> Toast.makeText(context, "Email уже используется", Toast.LENGTH_SHORT).show()
                "ERROR_WEAK_PASSWORD" -> Toast.makeText(context, "Пароль слишком слабый", Toast.LENGTH_SHORT).show()
                "ERROR_INVALID_EMAIL" -> Toast.makeText(context, "Неверный email", Toast.LENGTH_SHORT).show()
                "ERROR_WRONG_PASSWORD" -> Toast.makeText(context, "Неверный пароль", Toast.LENGTH_SHORT).show()
                "ERROR_USER_NOT_FOUND" -> Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                "ERROR_TOO_MANY_REQUESTS" -> Toast.makeText(context, "Слишком много запросов", Toast.LENGTH_SHORT).show()
                "ERROR_INVALID_CREDENTIAL" -> Toast.makeText(context, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                "ERROR_EMAIL_NOT_FOUND" -> Toast.makeText(context, "Email не найден", Toast.LENGTH_SHORT).show()
                "ERROR_OPERATION_NOT_ALLOWED" -> Toast.makeText(context, "Операция недоступна", Toast.LENGTH_SHORT).show()
                "ERROR_USER_DISABLED" -> Toast.makeText(context, "Пользователь отключен", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
        if (e is FirebaseAuthInvalidCredentialsException) {
            when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> Toast.makeText(context, "Неверный email", Toast.LENGTH_SHORT).show()
                "ERROR_INVALID_PASSWORD" -> Toast.makeText(context, "Неверный пароль", Toast.LENGTH_SHORT).show()
                "ERROR_WRONG_PASSWORD" -> Toast.makeText(context, "Неверный пароль", Toast.LENGTH_SHORT).show()
                "ERROR_USER_NOT_FOUND" -> Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                "ERROR_INVALID_CREDENTIAL" -> Toast.makeText(context, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }
}