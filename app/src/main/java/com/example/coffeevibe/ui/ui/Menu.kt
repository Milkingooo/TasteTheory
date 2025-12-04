package com.example.coffeevibe.ui.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.other.AssistChipMenu
import com.example.coffeevibe.ui.ui.other.UserOrderItem
import com.example.coffeevibe.utils.CashApplication
import com.example.coffeevibe.utils.NetworkUtils
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MenuScreen(
    orderVm: OrderViewModel,
    menuViewModel: MenuViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val networkAvailable by NetworkUtils.isNetworkAvailable(context).collectAsState(initial = true)
    val goods by menuViewModel.dataList.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }
    var showInfo by rememberSaveable { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }
    var selectedDescription by rememberSaveable { mutableStateOf("") }
    var selectedImage by rememberSaveable { mutableStateOf("") }
    var selectedName by rememberSaveable { mutableStateOf("") }
    val listState2 = rememberLazyGridState()
    val isOrderHas by menuViewModel.isOrderHas.collectAsState()
    val numAndPrice by menuViewModel.orderNP.collectAsState()
    val orderWas by menuViewModel.isOrderWas.collectAsState()
    val cartItems by orderVm.itemList.collectAsState()
    val scope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(1000)
            isRefreshing = false
            menuViewModel.loadMenu()
            menuViewModel.loadOrders()
        }
    }

    val filteredGoods = if (searchQuery.isBlank()) {
        goods
    } else {
        goods.filter {
            it.name.contains(searchQuery, true)
        }
    }

    val categories = filteredGoods.groupBy { it.category }.toSortedMap()
    val categoryIndexMap = remember { mutableStateMapOf<String, Int>() }

    if (showSheet) {
        AboutItemSheet(
            showSheet,
            description = selectedDescription,
            image = selectedImage,
            name = selectedName
        ) {
            showSheet = it
        }
    }

    if (orderWas) {
        menuViewModel.loadMenu()
        menuViewModel.loadOrders()
        menuViewModel.loadUserOrders()
        menuViewModel.updateOrderWas(false)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    CoffeeVibeTheme(content = {
        Scaffold()
        { innerPadding ->
            if (!networkAvailable) {
                NotInternet {
                    menuViewModel.loadMenu()
                    menuViewModel.loadOrders()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .consumeWindowInsets(innerPadding)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isSearching) {
                            Text(
                                text = "Меню",
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 28.sp,
                                textAlign = TextAlign.Left,
                            )
                        }
                        AnimatedVisibility(
                            visible = isSearching,
                            enter = fadeIn() + expandHorizontally(),
                            exit = shrinkHorizontally() + fadeOut()
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = {
                                    searchQuery = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorScheme.onBackground,
                                    unfocusedBorderColor = colorScheme.onSurface,
                                    unfocusedPlaceholderColor = colorScheme.onBackground,
                                    focusedTextColor = colorScheme.onBackground,
                                    unfocusedTextColor = colorScheme.onBackground,
                                ),
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Search,
                                        contentDescription = "Search",
                                        tint = colorScheme.onBackground
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "Поиск",
                                        color = colorScheme.onSurface
                                    )
                                },
                                shape = MaterialTheme.shapes.large,
                                singleLine = true,
                            )
                        }
                        IconButton(onClick = {
                            isSearching = !isSearching
                            searchQuery = ""
                        }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = colorScheme.onBackground,
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {
                        categories.keys.forEach { category ->
                            AssistChipMenu(
                                name = category,
                                click = {
                                    scope.launch {
                                        val index = categoryIndexMap[category] ?: 0
                                        listState2.animateScrollToItem(index)
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    PullToRefreshBox(
                        modifier = Modifier.padding(4.dp).weight(1f),
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
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 128.dp),
                            modifier = Modifier
//                                .weight(1f)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            state = listState2,
                        ) {
                            if (filteredGoods.isNotEmpty()) {
                                if (isOrderHas) {
                                    item(span = { GridItemSpan(2) }) {
                                        Spacer(modifier = Modifier.height(24.dp))

                                        val pagerState = rememberPagerState(pageCount = {
                                            numAndPrice.size
                                        })
                                        Column {
                                            HorizontalPager(
                                                state = pagerState,
                                                contentPadding = PaddingValues(end = 16.dp),
                                                pageSpacing = 6.dp
                                            ) {
                                                UserOrderItem(
                                                    number = numAndPrice[it].number,
                                                    price = numAndPrice[it].price,
                                                    pickupTime = numAndPrice[it].pickupTime,
                                                    state = numAndPrice[it].state
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(
                                                Modifier
                                                    .wrapContentHeight()
                                                    .fillMaxWidth()
                                                    .align(Alignment.CenterHorizontally)
                                                    .padding(bottom = 8.dp),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                repeat(pagerState.pageCount) { iteration ->
                                                    val color =
                                                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                                    Box(
                                                        modifier = Modifier
                                                            .padding(2.dp)
                                                            .clip(CircleShape)
                                                            .background(color)
                                                            .size(8.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                var currentIndex = 0

                                categories.forEach { (category, filteredGoods) ->

                                    categoryIndexMap[category] = currentIndex

                                    item(span = { GridItemSpan(2) }, key = category) {
                                        Text(
                                            text = category,
                                            color = colorScheme.onBackground,
                                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                            fontSize = 28.sp,
                                            textAlign = TextAlign.Left,
                                        )
                                    }
//                                    stickyHeader {
//                                        Text(
//                                            text = category,
//                                            color = colorScheme.onBackground,
//                                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
//                                            fontSize = 28.sp,
//                                            textAlign = TextAlign.Left,
//                                        )
//                                    }
                                    currentIndex++

                                    items(filteredGoods, key = { it.id }) { item ->
                                        ListItem2(
                                            name = item.name,
                                            price = item.price,
                                            image = item.image,
                                            onInfo = {
                                                //showInfo = true
                                                showSheet = true
                                                selectedDescription = item.description
                                                selectedImage = item.image
                                                selectedName = item.name
                                            },
                                            onAdd = {
                                                orderVm.addItem(
                                                    id = item.id,
                                                    name = item.name,
                                                    price = item.price,
                                                    image = item.image,
                                                    quantity = 1
                                                )
                                            },
                                            onDelete = {
                                                orderVm.deleteItemById(item.id)
                                            },
                                            isSelected = cartItems.any { cartItem ->
                                                cartItem.idItem == item.id
                                            },
                                            available = item.status
                                        )
                                    }
                                    currentIndex += filteredGoods.size + 1
                                }

                            }

                        }
                    }
                }
            }
        }
    })
}


@Composable
fun ListItem2(
    name: String,
    price: Int,
    image: String,
    onInfo: () -> Unit,
    onAdd: () -> Unit,
    isSelected: Boolean = false,
    onDelete: () -> Unit,
    available: String
) {

    CoffeeVibeTheme(content = {
        OutlinedCard(
            modifier = Modifier
                .width(175.dp)
                .height(270.dp)
                .shadow(2.dp, RoundedCornerShape(12.dp))
                .clickable {
                    onInfo()
                },
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.background,
            ),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                if (isSelected) 2.dp else 1.dp,
                if (isSelected) colorScheme.secondary else colorScheme.onSurface
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {

                val imageLoader = (LocalContext.current.applicationContext as CashApplication).imageLoader

                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = image)
                            .memoryCacheKey(image)
                            .diskCacheKey(image)
                            .error(R.drawable.error_load)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                transformations(RoundedCornersTransformation(10f))
                            }).build(),
                        placeholder = painterResource(id = R.drawable.placeholder),
                        imageLoader = imageLoader,
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(shape = RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop,

                )


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .animateContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    if (available == "Недоступен") {
                        Text(
                            text = "$price руб.",
                            color = colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.LineThrough,
                        )
//                        Icon(
//                            Icons.Filled.ErrorOutline,
//                            "Error",
//                            tint = colorScheme.error,
//                        )
                    } else {
                        Text(
                            text = "$price руб.",
                            color = colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            if (isSelected) {
                                onDelete()
                            } else {
                                onAdd()
                            }
                        },
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(color = if (available == "Недоступен") Color.LightGray else colorScheme.secondary),
                        enabled = available != "Недоступен"
                    ) {
                        if (!isSelected) {
                            Icon(
                                Icons.Filled.Add,
                                "Add",
                                tint = colorScheme.background,
                            )
                        } else {
                            Icon(
                                Icons.Filled.Delete,
                                "Remove",
                                tint = colorScheme.background,
                            )
                        }
                    }
                }
            }
        }
    })
}

@Composable
fun CachedImage(url: String) {
    Box {
        AsyncImage(
            model =
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true) // Плавный переход при загрузке нового изображения
                }).build(),
            contentDescription = null, // Описание для доступности
            modifier = Modifier
                .width(130.dp)
                .height(130.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop,
        )

        val painter = rememberAsyncImagePainter(url)
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(color = colorScheme.onBackground)
        }
    }
}