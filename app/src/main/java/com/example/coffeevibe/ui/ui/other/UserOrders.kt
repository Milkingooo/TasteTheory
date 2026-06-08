package com.example.coffeevibe.ui.ui.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.model.OrderManagerOrderItem
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.AboutItemSheet
import com.example.coffeevibe.ui.ui.customUi.SimpleBottomSheet
import com.example.coffeevibe.utils.TimeUtils
import com.example.coffeevibe.viewmodel.MenuViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserOrdersScreen(
    menuViewModel: MenuViewModel
) {
    val orders by menuViewModel.userOrders.collectAsState()
    val isLoading by menuViewModel.isOrdersLoad.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showDetails by remember { mutableStateOf(false) }
    var selectedId by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    val sortedOrders = remember(
        orders
    ) {
        orders.sortedByDescending {
            TimeUtils.convertToMills(it.date)
        }
    }

    val ordersByDate =
        sortedOrders.groupBy { TimeUtils.convertToDateWithFormat(it.date, "EEE, dd MMM yyyy") }


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

    if (showDetails) {
        SimpleBottomSheet(state = showDetails,
            onDismiss = { showDetails = false },
            content = {
            var orderDetails by remember { mutableStateOf(listOf<OrderManagerOrderItem>()) }
            var load by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                menuViewModel.getOrderDetails(
                    selectedId,
                    isLoading = {
                        load = it
                    },
                    callback = {
                        orderDetails = it
                    }
                )
            }

            if (load) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IndeterminateCircularIndicator()
                }
            } else {
                Text(
                    text = "Заказ №$selectedId",
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Состав",
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                orderDetails.forEach {
                    Text(
                        text = "${it.quantity} шт. ${it.name}",
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.Left,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        )
    }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                    colors=TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background),
                    scrollBehavior = scrollBehavior
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
            else if(sortedOrders.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Тут пока что пусто",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )
                }
            } else {
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
                        ordersByDate.forEach { (date, orders) ->
                            item(key = date) {
                                Text(
                                    text = date.toString(),
                                    color = colorScheme.onBackground,
                                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Left,
                                )
                            }

                            items(orders, key = { it.number }) { order ->
                                UserOrder(
                                    price = order.price,
                                    number = order.number,
                                    dateOrder = order.date,
                                    state = order.state
                                ) {
                                    selectedId = order.number.toInt()
                                    showDetails = true
                                }
                            }
                        }
                    }
                }
            }
        }
    })
}