package com.example.coffeevibe.ui.activities

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.activities.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.OrderFinish
import com.example.coffeevibe.ui.ui.OrderFinishPreview
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderFinishViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val passwordDb = CartDatabase.getDatabase(context)
            val passwordDao = passwordDb.cartDao()
            val repository = CartRepository(passwordDao)
            val orderViewModel = OrderViewModel(repository, context)
            val orderFVm = OrderFinishViewModel()
            val menuVm = MenuViewModel(context)
            OrderFinish({
                finish()
            }, orderVm = orderViewModel,
                menuVm = menuVm,
                orderFinishVm = orderFVm
            )
        }
    }
}
