package com.example.coffeevibe.ui.ui

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.customUi.MenuTopBar
import com.example.coffeevibe.ui.ui.other.AssistChipMenu
import com.example.coffeevibe.ui.ui.other.IndeterminateCircularIndicator
import com.example.coffeevibe.ui.ui.other.OrderCard
import com.example.coffeevibe.ui.ui.other.UserOrderItem
import com.example.coffeevibe.utils.CashApplication
import com.example.coffeevibe.utils.NetworkUtils
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class AboutSheetData(
    var name: String = "",
    var description: String = "",
    var image: String = "",
    var composition: String = "",
    var kbju: String = ""
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MenuScreen(
    orderVm: OrderViewModel,
    menuViewModel: MenuViewModel,
) {
    val context = LocalContext.current
    val networkAvailable by NetworkUtils.isNetworkAvailable(context).collectAsState(initial = true)
    val goods by menuViewModel.dataList.collectAsState()

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }
    var onlyDiscount by rememberSaveable { mutableStateOf(false) }
    var priceDecreasing  by rememberSaveable { mutableStateOf(false) }
    var priceIncreasing by rememberSaveable { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val listState2 = rememberLazyGridState()
    val isOrderHas by menuViewModel.isOrderHas.collectAsState()
    val numAndPrice by menuViewModel.orderNP.collectAsState()
    val orderWas by menuViewModel.isOrderWas.collectAsState()
    val isLoading by menuViewModel.isMenuLoad.collectAsState()
    val cartItems by orderVm.itemList.collectAsState()
    val scope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        scope.launch {
            delay(1000)
            isRefreshing = false
            menuViewModel.loadMenu()
            menuViewModel.loadOrders()
        }
    }

    val filteredGoods by remember (
        goods,
        searchQuery,
        onlyDiscount,
        priceIncreasing,
        priceDecreasing
    ) {
        derivedStateOf {
            var result = goods

            if (searchQuery.isNotBlank()) {
                result = result.filter {
                    it.name.contains(searchQuery, true) ||
                            it.price.toString().contains(searchQuery, true) ||
                            it.discountPrice.toString().contains(searchQuery, true)
                }
            }

            if (onlyDiscount) {
                result = result.filter {
                    it.discountPrice > 0
                }
            }

            result = when {
                priceIncreasing -> result.sortedBy { it.price }
                priceDecreasing -> result.sortedByDescending { it.price }
                else -> result
            }

            result
        }
    }

    val categories = filteredGoods.groupBy { it.category }.toSortedMap()
    val categoryIndexMap = remember { mutableStateMapOf<String, Int>() }
    var itemIndex = 0

    categories.forEach { (category, items) ->
        categoryIndexMap[category] = itemIndex
        itemIndex += items.size + 1
    }

    val aboutSheetData by remember { mutableStateOf(AboutSheetData()) }

    if (showSheet) {
        AboutItemSheet(
            showSheet,
            description = aboutSheetData.description,
            image = aboutSheetData.image,
            name = aboutSheetData.name,
            composition = aboutSheetData.composition,
            kbju = aboutSheetData.kbju,
        ) {
            showSheet = false
        }
    }

    LaunchedEffect(orderWas) {
        if (orderWas) {
            menuViewModel.loadMenu()
            menuViewModel.loadOrders()
            menuViewModel.loadUserOrders()
            menuViewModel.updateOrderWas(false)
        }
    }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            floatingActionButton = {
                val displayButton by remember { derivedStateOf { listState2.firstVisibleItemIndex > 4 } }

                AnimatedVisibility(visible = displayButton) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                listState2.animateScrollToItem(0)
                            }
                        },
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onBackground,
                    ) {
                        Icon(
                            Icons.Filled.KeyboardDoubleArrowUp,
                            "Вверх",
                            tint = colorScheme.background
                        )
                    }
                }
            },
            topBar = {
                MenuTopBar(
                    title = stringResource(R.string.Menu),
                    searchQuery = searchQuery,
                    isSearching = isSearching,
                    onSearchQueryChange = { searchQuery = it },
                    onSearchToggle = {
                        isSearching = !isSearching
                        if (!isSearching) searchQuery = ""
                    },
                    onlyDiscount = onlyDiscount,
                    priceIncreasing = priceIncreasing,
                    priceDecreasing = priceDecreasing,
                    onOnlyDiscountChange = { onlyDiscount = it },
                    onPriceIncreasingChange = { priceIncreasing = it },
                    onPriceDecreasingChange = { priceDecreasing = it },
                    scrollBehavior = scrollBehavior
                )
            }
        )
        { innerPadding ->
            if (!networkAvailable) {
                NotInternet {
                    menuViewModel.loadMenu()
                    menuViewModel.loadOrders()
                }
            }
            else if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    IndeterminateCircularIndicator()
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    PullToRefreshBox(
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),
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
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            state = listState2,
                        ) {
                            stickyHeader() {
                                Row(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .shadow(
                                            4.dp,
                                            Shapes.large,
                                            spotColor = colorScheme.secondary
                                        )
                                        .fillMaxWidth()
                                        .clip(Shapes.large)
                                        .background(colorScheme.background),
                                    horizontalArrangement = Arrangement.SpaceAround
                                )
                                {
                                    categories.keys.forEach { category ->
                                        AssistChipMenu(
                                            name = category,
                                            click = {
                                                scope.launch {
                                                    val index =
                                                        categoryIndexMap[category] ?: 0
                                                    listState2.animateScrollToItem(index)
                                                }
                                            }
                                        )
                                    }
                                }
                            }

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
                                                contentPadding = if (numAndPrice.size > 1) PaddingValues(end = 16.dp) else PaddingValues(0.dp),
                                                pageSpacing = 6.dp,
                                                modifier = Modifier.animateItem()
                                            ) {
                                                OrderCard(
                                                    number = numAndPrice[it].number,
                                                    price = numAndPrice[it].price,
                                                    pickupTime = numAndPrice[it].pickupTime,
                                                    state = numAndPrice[it].state,
                                                    cancelOrder = {
                                                        menuViewModel.cancelOrder(numAndPrice[it].id)
                                                    },
                                                    orderDate = numAndPrice[it].date
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))

                                            PageIndicator(
                                                numberOfPages = pagerState.pageCount,
                                                selectedPage = pagerState.currentPage,
                                                modifier = Modifier
                                                    .wrapContentHeight()
                                                    .fillMaxWidth()
                                                    .align(Alignment.CenterHorizontally)
                                                    .padding(bottom = 8.dp),
                                                defaultColor = colorScheme.secondaryContainer,
                                                selectedColor = colorScheme.onSecondaryContainer
                                            )
                                        }
                                    }
                                }

                                categories.forEach { (category, filteredGoods) ->

                                    item(span = { GridItemSpan(2) }, key = category) {
                                        Text(
                                            text = category,
                                            color = colorScheme.onBackground,
                                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                            fontSize = 28.sp,
                                            textAlign = TextAlign.Left,
                                        )
                                    }

                                    items(filteredGoods, key = { it.id }) { item ->
                                        ListItem2(
                                            name = item.name,
                                            price = item.price,
                                            discountPrice = item.discountPrice,
                                            image = item.image,
                                            onInfo = {
                                                aboutSheetData.name = item.name
                                                aboutSheetData.description = item.description
                                                aboutSheetData.image = item.image
                                                aboutSheetData.kbju = item.kbju
                                                aboutSheetData.composition = item.composition
                                                showSheet = true
                                            },
                                            onAdd = {
                                                orderVm.addItem(
                                                    id = item.id,
                                                    name = item.name,
                                                    price = if (item.discountPrice == 0) item.price else item.discountPrice,
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
    discountPrice: Int,
    image: String,
    onInfo: () -> Unit,
    onAdd: () -> Unit,
    isSelected: Boolean = false,
    onDelete: () -> Unit,
    available: String
) {

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Card(
            modifier = Modifier
                .width(175.dp)
                .height(270.dp)
                //.shadow(4.dp, Shapes.large)
                .shadow(
                    4.dp,
                    Shapes.large,
                    spotColor = if (isSelected) colorScheme.primary else colorScheme.secondary
                )
                .clickable {
                    onInfo()
                },
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.background,
            ),
            shape = Shapes.large,
            border = BorderStroke(
                if (isSelected) 2.dp else 0.dp,
                if (isSelected) colorScheme.primary else colorScheme.outline
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {

                val imageLoader = (LocalContext.current.applicationContext as CashApplication).imageLoader

                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = image)
                        .crossfade(true)
                        .memoryCacheKey(image)
                        .diskCacheKey(image)
                        .transformations(RoundedCornersTransformation(10f))
                        .error(R.drawable.error_load)
                        .build(),
                    imageLoader = imageLoader
                )
                val state = painter.state
                val transition by animateFloatAsState(
                    targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f
                )

                Box(
                    modifier = Modifier
                        .size(150.dp)
                )
                {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(140.dp)
                            .clip(shape = Shapes.medium)
                            .alpha(transition)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                    )

                    if (discountPrice != 0) {
                        Text(
                            "Хит",
                            color = colorScheme.background,
                            modifier = Modifier
                                .width(60.dp)
                                .padding(4.dp)
                                .offset(x = (-10).dp, y = -(10).dp)
                                .clip(Shapes.medium)
                                .background(colorScheme.primary)
                                .align(Alignment.TopStart),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .animateContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    if (available == "Недоступен") {
                        Text(
                            text = "$price руб.",
                            color = colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 18.sp
                        )
                    } else {
                        if (discountPrice == 0) {
                            Text(
                                text = "$price₽",
                                color = colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                        } else {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.height(75.dp)
                            ){
                                Text(
                                    text = "$price₽",
                                    color = colorScheme.onBackground,
                                    textAlign = TextAlign.Right,
                                    textDecoration = TextDecoration.LineThrough,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "$discountPrice₽",
                                    color = Color.Black,
                                    textAlign = TextAlign.Left,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .background(Color(0xFFFDD835), Shapes.small)
                                        .padding(4.dp)
                                )
                            }
                        }
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
                            .background(color = if (available == "Недоступен") Color.LightGray else colorScheme.secondaryContainer),
                        enabled = available != "Недоступен"
                    ) {
                        if (!isSelected) {
                            Icon(
                                Icons.Filled.Add,
                                "Add",
                                tint = colorScheme.onSecondaryContainer,
                            )
                        } else {
                            Icon(
                                Icons.Filled.Delete,
                                "Remove",
                                tint = colorScheme.onSecondaryContainer,
                            )
                        }
                    }
                }
            }
        }
    })
}

@Composable
fun PageIndicator(
    numberOfPages: Int,
    selectedPage: Int = 0,
    selectedColor: Color = Color.White,
    defaultColor: Color = Color.Gray,
    defaultRadius: Dp = 8.dp,
    selectedLength: Dp = 25.dp,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        repeat(numberOfPages) {
            Indicator(
                isSelected = it == selectedPage,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
            )
        }
    }
}

/**
 * pager indicator item
 */
@Composable
fun Indicator(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    modifier: Modifier = Modifier.height(defaultRadius)
) {
    val width by animateDpAsState(
        targetValue = if (isSelected) selectedLength else defaultRadius,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Box(
        modifier = modifier
            .width(width)
            .clip(CircleShape)
            .background(color = if (isSelected) selectedColor else defaultColor)
    )
}