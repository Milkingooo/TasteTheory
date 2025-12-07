package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PlaylistAddCircle
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.other.SettingsSubCategory
import com.example.coffeevibe.viewmodel.LoginViewModel


@Composable
fun MainAdminScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Главная страница администратора")
    }
}
@Composable
fun ProductsAdminScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Управление товарами")
    }
}
@Composable
fun OrdersAdminScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Управление заказами")
    }
}

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    MAIN("main", "Главная", Icons.Default.Star, "Main"),
    PRODUCTS("products", "Товары", Icons.Default.Fastfood, "Products"),
    ORDERS("orders", "Заказы", Icons.Default.List, "Orders"),
    CONTENT("content", "Контент", Icons.Default.Share, "Content"),
    USERS("users", "Пользователи", Icons.Default.People, "Users"),
    SAFETY("safety", "Безопасность", Icons.Default.Security, "Safety"),
    SUPPORT("support", "Поддержка", Icons.Default.ContactSupport, "Support")
}
data class TabItems(
    val title: String,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val screen: @Composable () ->Unit
)

val tabItems = listOf(
    TabItems(
        title = "Главная",
        unSelectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        screen = { MainAdminScreen() }
    ),
    TabItems(
        title = "Товары",
        unSelectedIcon = Icons.Outlined.Fastfood,
        selectedIcon = Icons.Filled.Fastfood,
        screen = {  }
    ),
    TabItems(
        title = "Заказы",
        unSelectedIcon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart,
        screen = { }
    ),
    TabItems(
        title = "Контент",
        unSelectedIcon = Icons.Outlined.Money,
        selectedIcon = Icons.Filled.Money,
        screen = { }
    ),
    TabItems(
        title = "Пользователи",
        unSelectedIcon = Icons.Outlined.People,
        selectedIcon = Icons.Filled.People,
        screen = { }
    ),
    TabItems(
        title = "Безопасность",
        unSelectedIcon = Icons.Outlined.Security,
        selectedIcon = Icons.Filled.Security,
        screen = { }
    ),
    TabItems(
        title = "Поддержка",
        unSelectedIcon = Icons.Outlined.SupportAgent,
        selectedIcon = Icons.Filled.SupportAgent,
        screen = { }
    ),
)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.MAIN -> MainAdminScreen()
                    Destination.USERS -> TODO()
                    Destination.ORDERS -> TODO()
                    Destination.PRODUCTS -> TODO()
                    Destination.CONTENT -> TODO()
                    Destination.SAFETY -> TODO()
                    Destination.SUPPORT -> TODO()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    onBackPressed: () -> Unit
) {
    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .consumeWindowInsets(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Администрирование",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )

                    IconButton(
                        onClick = {
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                }

//                val navController = rememberNavController()
//                val startDestination = Destination.MAIN
//                var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
//
//
//                PrimaryScrollableTabRow(
//                    selectedTabIndex = selectedDestination,
//                    containerColor = colorScheme.background) {
//                        Destination.entries.forEachIndexed { index, destination ->
//                            Tab(
//                                selected = selectedDestination == index,
//                                onClick = {
//                                    navController.navigate(route = destination.route)
//                                    selectedDestination = index
//                                },
//                                text = {
//                                    Text(
//                                        text = destination.label,
//                                        maxLines = 1,
//                                        overflow = TextOverflow.Ellipsis,
//                                        fontSize = 16.sp,
//                                    )
//                                }
//                            )
//                        }
//                    }
//                    AppNavHost(navController, startDestination)
                var selectedTabIndex by remember {
                    mutableStateOf(0)
                }
                val pagerState = rememberPagerState {
                    tabItems.size
                }
                LaunchedEffect(selectedTabIndex) {
                    pagerState.animateScrollToPage(selectedTabIndex)
                }
                LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                    if (!pagerState.isScrollInProgress) {
                        selectedTabIndex = pagerState.currentPage
                    }
                }

                PrimaryScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = colorScheme.background,
                    edgePadding = 0.dp) {
                    tabItems.fastForEachIndexed { index, item ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(
                                    text = item.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 16.sp,
                                    )
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedTabIndex) {
                                        item.selectedIcon
                                    } else item.unSelectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { index ->
                    when (index) {
                        0 -> MainAdminScreen()
                        1 -> ProductsAdminScreen()
                        2 -> OrdersAdminScreen()
                    }
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NavigationTabExample(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.MAIN
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(modifier = modifier) { contentPadding ->
        PrimaryScrollableTabRow(selectedTabIndex = selectedDestination, modifier = Modifier.padding(contentPadding)) {
            Destination.entries.forEachIndexed { index, destination ->
                Tab(
                    selected = selectedDestination == index,
                    onClick = {
                        navController.navigate(route = destination.route)
                        selectedDestination = index
                    },
                    text = {
                        Text(
                            text = destination.label,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                )
            }
        }
        AppNavHost(navController, startDestination)
    }
}