package com.example.coffeevibe.ui.ui.adminPanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Close
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
import com.example.coffeevibe.ui.ui.customUi.MaterialList
import com.example.coffeevibe.ui.ui.customUi.MaterialListItem
import com.example.coffeevibe.ui.ui.other.IndeterminateCircularIndicator
import com.example.coffeevibe.ui.ui.other.ManagerOrdersListItem
import com.example.coffeevibe.viewmodel.ManagerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OrdersAdmin(){
    val managerViewModel = ManagerViewModel()

    val selectedLocation by remember { mutableStateOf(1)}
    val orders by managerViewModel.ordersList.collectAsState()
    val isLoading by managerViewModel.isOrdersLoad.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(1000)
            isRefreshing = false
            managerViewModel.loadManagerOrders(selectedLocation)
        }
    }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0)) { innerPadding ->
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
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        if (orders.isEmpty()) {
                            item {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "Localized description",
                                    tint = colorScheme.onBackground,
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            }
                        }
                        items(orders, key = { it.id }) { order ->
                            ManagerOrdersListItem(
                                order = order,
                                onUpdate = {
                                    managerViewModel.updateOrderState(order.id, it)
                                },
                                state = order.state
                            )
                        }
                    }
                }
            }
        }
    })
}
