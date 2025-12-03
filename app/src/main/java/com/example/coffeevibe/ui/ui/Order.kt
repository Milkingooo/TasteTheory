package com.example.coffeevibe.ui.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.utils.AuthUtils
import com.example.coffeevibe.viewmodel.OrderViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    onCreateOrder: () -> Unit,
    orderVm: OrderViewModel
) {
    val haptic = LocalHapticFeedback.current
    val orderItems by orderVm.itemList.collectAsState()
    val totalPrice by orderVm.total.collectAsState()
    val context = LocalContext.current

    CoffeeVibeTheme(content = {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = { },
                    floatingActionButton = {
                        FloatingActionButton(
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
                            containerColor = if (orderItems.isNotEmpty() && AuthUtils.isUserAuth()) colorScheme.secondary else Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
//                                .wrapContentHeight()
                                .clip(RoundedCornerShape(16.dp)),
                            ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Payments,
                                    "Localized description",
                                    tint = colorScheme.background
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "К оформлению",
                                    color = colorScheme.background,
                                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                    fontSize = 16.sp,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "$totalPrice₽",
                                    modifier = Modifier
                                        .width(130.dp)
                                        .weight(1f),
                                    color = colorScheme.background,
                                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .background(color = colorScheme.background)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
//                        .wrapContentHeight()
                        .padding(start = 12.dp),
                    containerColor = colorScheme.background,
                )
            },
            modifier = Modifier
//                .fillMaxSize()
                .background(colorScheme.background)
        ) { innerPadding ->
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
                        text = "Корзина",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )

                    IconButton(onClick = { orderVm.deleteAllItems() }) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(orderItems, key = { it.id }) {
                        var isVisible by remember { mutableStateOf(true) }
                        val coroutineScope = rememberCoroutineScope()

                        AnimatedVisibility(
                            visible = isVisible,
                            exit = shrinkVertically() + fadeOut())
                        {
                            CartItem(
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
        }
    })
}

@Composable
fun CartItem(
    name: String,
    price: Int,
    image: String,
    quantity: Int,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {

    var scale by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(quantity) {
        scale = 1.2f

        animate(
            initialValue = 1.2f,
            targetValue = 1f,
            animationSpec = tween(150)
        ) { value, _ ->
                scale = value
            }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .background(colorScheme.surface, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current).data(data = image)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true) // Плавный переход при загрузке нового изображения
                    }).build(),
                contentDescription = null, // Описание для доступности
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
                    .clip(shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    color = colorScheme.onBackground,
                    //modifier = Modifier.width(150.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "$price руб.", color = colorScheme.onBackground)

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = {
                        onMinus()
                    }) {
                        Icon(
                            Icons.Filled.Remove,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                    }

                    Text(
                        text = quantity.toString(),
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                        modifier = Modifier.scale(scale)
                    )

                    IconButton(onClick = {
                        onPlus()
                    }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissListItems(
    name: String,
    price: Int,
    image: String,
    quantity: Int,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    var isVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    if (isVisible) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                val color by
                animateColorAsState(
                    when (dismissState.targetValue) {
                        SwipeToDismissBoxValue.Settled -> Color.LightGray
                        SwipeToDismissBoxValue.StartToEnd -> Color.Green
                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                    }, label = ""
                )
                Box(Modifier.fillMaxSize().background(color))
            }
        ) {
            CartItem(
                name,
                price,
                image,
                quantity,
                onPlus,
                onMinus = {
                    isVisible = false
                }
            )
        }
    }
}