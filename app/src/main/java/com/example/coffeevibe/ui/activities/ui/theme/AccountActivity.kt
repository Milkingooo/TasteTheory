package com.example.coffeevibe.ui.activities.ui.theme

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coffeevibe.ui.activities.LoginActivity
import com.example.coffeevibe.ui.activities.ui.theme.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.AccountScreen

class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccountScreen(
                logOut = {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            )
        }
    }
}
