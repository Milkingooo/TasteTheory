package com.example.coffeevibe.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.other.TestScreen

class TestActivity : android.window.SplashScreen {

    override fun clearOnExitAnimationListener() {
        TODO("Not yet implemented")
    }

    override fun setOnExitAnimationListener(p0: android.window.SplashScreen.OnExitAnimationListener) {
        TODO("Not yet implemented")
    }

    override fun setSplashScreenTheme(p0: Int) {
        TODO("Not yet implemented")
    }
}