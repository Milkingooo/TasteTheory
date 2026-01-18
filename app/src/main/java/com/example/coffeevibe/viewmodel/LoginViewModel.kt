package com.example.coffeevibe.viewmodel

import android.R.attr.password
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class LoginViewModel(val context: Context) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _userRole = MutableStateFlow(5)
    val userRole: StateFlow<Int> = _userRole

    private val _isUserLogin = MutableStateFlow(false)
    val isUserLogin: StateFlow<Boolean> = _isUserLogin

    init {
        if (auth.currentUser != null ) {
            getNameAndStatus()
        }
    }

    fun getNameAndStatus() {
        _isUserLogin.value = isLogin()

        db.collection("Client")
            .whereEqualTo("Id", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    _username.value = document.getString("Name").toString()

                    val isAdmin = document.getBoolean("IsAdmin") ?: false
                    val isManager = document.getBoolean("IsManager") ?: false

                    when {
                        !isAdmin && !isManager -> _userRole.value = 2
                        isAdmin -> _userRole.value = 0
                        else -> _userRole.value = 1
                    }

                    Log.e("CHECK_ROLES", "ROLE: ${_userRole.value}")
                    break
                }
            }
            .addOnFailureListener {
                _username.value = "Ошибка"
                _userRole.value = 2
                Log.e("CHECK_ROLES", "ERROR")
            }
    }
    fun login(login: String, password: String, isLogin: (Boolean, Int) -> Unit) {
        if (auth.currentUser != null) {
            isLogin(true, _userRole.value)
        }

        try {
            if (login.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(login, password)
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            getNameAndStatus()
                            delay(400)
                            isLogin(true, _userRole.value)
                        Toast.makeText(context, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        isLogin(false, _userRole.value)
                        catchException(e)
                        Log.d("Login", e.message.toString())
                    }
            } else {
                isLogin(false, _userRole.value)
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            isLogin(false, _userRole.value)
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
                .document(id) // Используем UID как ID документа
                .set(
                    hashMapOf(
                        "Name" to name,
                        "Email" to email,
                        "Id" to id,
                        "IsAdmin" to false,
                        "IsManager" to false,
                        "CreatedAt" to FieldValue.serverTimestamp(),
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

    fun giveUserNameEmail(getNameEmail: (String, String) -> Unit) {
        db.collection("Client")
            .whereEqualTo("Id", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    getNameEmail(document.getString("Name").toString(), document.getString("Email").toString())
                    _username.value = document.getString("Name").toString()
                    break
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error getting user name", Toast.LENGTH_SHORT).show()
                getNameEmail("Нет имени!", "Нет почты!")
            }
    }

    private fun changeNameInDb(newName: String) {
        viewModelScope.launch {
            db.collection("Client")
                .document(auth.currentUser?.uid.toString()).update("Name", newName)
                .await()
        }
    }

    fun isLogin(): Boolean {
        return auth.currentUser != null
    }

    fun updateUserProfile(
        newEmail: String,
        newPassword: String,
        oldPassword: String,
        newName: String,
    ){
        when {
            newName.isNotEmpty() -> {
                changeNameInDb(newName)
            }
            newEmail.isNotEmpty() -> {
                val user = auth.currentUser
                val credential = EmailAuthProvider.getCredential(user?.email!!, oldPassword)

                user.reauthenticate(credential)
                    .addOnSuccessListener {
                        user.updateEmail(newEmail)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Email успешно обновлен", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Ошибка обновления email: ${e.message}", Toast.LENGTH_SHORT).show()
                            }

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Ошибка переаутентификации: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            newPassword.isNotEmpty() -> {
                val user = auth.currentUser
                val credential = EmailAuthProvider.getCredential(user?.email!!, oldPassword)

                user.reauthenticate(credential)
                    .addOnSuccessListener {
                        user.updatePassword(newPassword)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Пароль успешно обновлен", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Ошибка обновления пароля: ${e.message}", Toast.LENGTH_SHORT).show()
                            }

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Ошибка переаутентификации: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            else -> {

            }
        }
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