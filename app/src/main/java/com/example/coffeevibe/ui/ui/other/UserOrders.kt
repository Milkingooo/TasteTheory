package com.example.coffeevibe.ui.ui.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserOrdersScreen(
    menuViewModel: MenuViewModel
) {
    val orders by menuViewModel.userOrders.collectAsState()
    val isLoading by menuViewModel.isOrdersLoad.collectAsState()

    val sortedOrders = orders.sortedByDescending { it.date }

    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(1000)
            isRefreshing = false
            menuViewModel.loadUserOrders()
        }
    }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                        text = "Ваши заказы",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )},
                    windowInsets = TopAppBarDefaults.windowInsets,
                    colors=TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background)
                )
        }) { innerPadding ->
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    IndeterminateCircularIndicator()
                }
            }
            else {
                PullToRefreshBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(4.dp),
                    state = state,
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                    indicator = {
                        PullToRefreshDefaults.LoadingIndicator(
                            state = state,
                            isRefreshing = isRefreshing,
                            containerColor = colorScheme.secondary,
                            color = colorScheme.background,
                            modifier = Modifier.align(Alignment.TopCenter),
                        )
                    },
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
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
        }
    })
}