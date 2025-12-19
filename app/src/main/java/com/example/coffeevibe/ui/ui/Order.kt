package com.example.coffeevibe.ui.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.other.CartItemNew
import com.example.coffeevibe.utils.AuthUtils
import com.example.coffeevibe.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onCreateOrder: () -> Unit,
    orderVm: OrderViewModel
) {
    val haptic = LocalHapticFeedback.current
    val orderItems by orderVm.itemList.collectAsState()
    val totalPrice by orderVm.total.collectAsState()
    val context = LocalContext.current

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Корзина",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 28.sp,
                            textAlign = TextAlign.Left
                        )
                    },
                    actions = {
                        IconButton(onClick = { orderVm.deleteAllItems() }) {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = "Localized description",
                                tint = colorScheme.onBackground,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    },
                    windowInsets = TopAppBarDefaults.windowInsets,
                    colors=TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background)
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        if (orderItems.isNotEmpty() && AuthUtils.isUserAuth()) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onCreateOrder()
                        } else if (!AuthUtils.isUserAuth()) {
                            Toast.makeText(
                                context,
                                "Пожалуйста авторизируйтесь",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(context, "Корзина пуста", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    colors = if (orderItems.isNotEmpty() && AuthUtils.isUserAuth())
                        ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary
                        )
                        else ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                    shape = Shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
                        .height(52.dp),

                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.ShoppingBasket,
                            "Localized description",
                            tint = colorScheme.background
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "К оформлению",
                            color = colorScheme.background,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .width(130.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "$totalPrice₽",
                            modifier = Modifier
                                .width(100.dp)
                                .weight(1f),
                            color = colorScheme.background,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }
        ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(orderItems, key = { it.id }) {
                        var isVisible by remember { mutableStateOf(true) }
                        val coroutineScope = rememberCoroutineScope()

                        Column(modifier = Modifier.animateItem())
                        {
                            CartItemNew(
                                name = it.name,
                                price = it.price,
                                image = it.image,
                                quantity = it.quantity,
                                onPlus = {
                                    if (it.quantity <= 9) {
                                        orderVm.updateItem(it, it.quantity + 1)
                                    } else haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                },
                                onMinus = {
                                    if (it.price > 1) {
                                        orderVm.updateItem(it, it.quantity - 1)
                                    } else {
                                        isVisible = false

                                        coroutineScope.launch {
                                            delay(500)
                                            orderVm.deleteItem(it)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
        }
    })
}