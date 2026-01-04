package com.example.coffeevibe.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.MainScreen
import com.example.coffeevibe.viewmodel.LoginViewModel
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeeVibeTheme(
                context2 = LocalContext.current
            ) {
                FirebaseApp.initializeApp(this)
                val passwordDb = CartDatabase.getDatabase(applicationContext)
                val passwordDao = passwordDb.cartDao()
                val repository = CartRepository(passwordDao)
                val orderViewModel = OrderViewModel(repository, applicationContext)
                val loginVm = LoginViewModel(applicationContext)
                val menuVm = MenuViewModel(context = applicationContext)
                val userRole = loginVm.userRole.collectAsState()

//                if (userRole.value == 1) {
//                    startActivity(Intent(this, ManagerActivity::class.java))
//                    finish()
//                }
//                else {
                MainScreen(
                    onLogin = {
                        startActivity(Intent(this, LoginActivity::class.java))
                    },
                    inFinishOrder = {
                        startActivity(Intent(this, OrderActivity::class.java))
                    },
                    menuViewModel = menuVm,
                    orderViewModel = orderViewModel,
                    loginVm = loginVm
                )
            }
        }
    }
}
