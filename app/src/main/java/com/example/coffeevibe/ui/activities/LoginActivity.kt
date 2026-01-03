package com.example.coffeevibe.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.coffeevibe.ui.ui.LoginScreen
import com.example.coffeevibe.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val loginVm = LoginViewModel(applicationContext)

        setContent {
            LoginScreen(
                inReg = {
                    startActivity(Intent(this, RegActivity::class.java))
                    finish()
                },
                onLogin = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                inManager = {
                    startActivity(Intent(this, ManagerActivity::class.java))
                    finish()
                },
                loginVm = loginVm
            )
        }
    }
}
