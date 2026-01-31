package com.example.coffeevibe.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeevibe.ui.ui.adminPanel.AddEditProductScreen
import com.example.coffeevibe.viewmodel.MenuViewModel

class ProductEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val menuVm = MenuViewModel(context = applicationContext)

        val arguments = intent.extras
        val product = arguments?.getInt("id")

        setContent {
            AddEditProductScreen(
                menuViewModel = menuVm,
                id = product
            )
        }
    }
}