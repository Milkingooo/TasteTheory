package com.example.coffeevibe.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.ui.MainScreen
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseApp.initializeApp(this)
            val passwordDb = CartDatabase.getDatabase(applicationContext)
            val passwordDao = passwordDb.cartDao()
            val repository = CartRepository(passwordDao)
            val orderViewModel = OrderViewModel(repository, applicationContext)

            MainScreen(
                onLogin = {
                    startActivity(Intent(this, LoginActivity::class.java))
                },
                inFinishOrder = {
                    startActivity(Intent(this, OrderActivity::class.java))
                },
                menuViewModel = MenuViewModel(context = applicationContext),
                orderViewModel = orderViewModel
            )
        }
    }
}
