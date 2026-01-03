package com.example.coffeevibe.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.coffeevibe.ui.ui.Registr
import com.example.coffeevibe.viewmodel.LoginViewModel

class RegActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val loginVm = LoginViewModel(applicationContext)
        setContent {
            Registr(
                inLogin = {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                isReg = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                loginVm = loginVm
            )
        }
    }
}
