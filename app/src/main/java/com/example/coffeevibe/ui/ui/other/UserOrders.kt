package com.example.coffeevibe.ui.ui.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun UserOrdersScreen(
    menuViewModel: MenuViewModel
) {
    val orders by menuViewModel.userOrders.collectAsState()
    menuViewModel.loadUserOrders()
    val sortedOrders = orders.sortedByDescending { it.date }

    CoffeeVibeTheme(content = {
        Scaffold() { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .consumeWindowInsets(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Ваши заказы тут",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
//                    sortedOrders.forEach { (date, orders) ->
//
//                        item {
////                            val parts = date.split("=") // Разбиваем строку на части
////                            val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
////                            val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
////                            val milliseconds = seconds * 1000 + nanoseconds / 1_000_000
////                            val date = Date(milliseconds)
////                            val format2 = SimpleDateFormat("EEE, dd MMM yyyy")
////                            val timeString = format2.format(date)
//
//                            Text(
//                                text = date.toString(),
//                                color = colorScheme.onBackground,
//                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
//                                fontSize = 28.sp,
//                                textAlign = TextAlign.Left,
//                            )
//                        }

                        items(sortedOrders, key = { it.number }) { order ->
                            UserOrder(
                                price = order.price,
                                number = order.number,
                                dateOrder = order.date
                            )
                        }

                }
            }
        }
    })
}