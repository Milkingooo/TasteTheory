package com.example.coffeevibe.ui.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffeevibe.ui.activities.ui.theme.Typography
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.adminPanel.AddEditProductScreen
import com.example.coffeevibe.ui.ui.other.ProfileScreen
import com.example.coffeevibe.ui.ui.settings.TestScreen
import com.example.coffeevibe.ui.ui.settings.SettingsScreen
import com.example.coffeevibe.ui.ui.settings.SupportScreen
import com.example.coffeevibe.ui.ui.other.UserOrdersScreen
import com.example.coffeevibe.ui.ui.settings.AccountScreen
import com.example.coffeevibe.ui.ui.settings.ErrorTicketSendScreen
import com.example.coffeevibe.ui.ui.settings.Faqs
import com.example.coffeevibe.viewmodel.LoginViewModel
import com.example.coffeevibe.viewmodel.ManagerViewModel
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel

@Composable
fun MainScreen(
    inFinishOrder: () -> Unit,
    onLogin: () -> Unit,
    menuViewModel: MenuViewModel,
    orderViewModel: OrderViewModel,
    loginVm: LoginViewModel,
    managerVm: ManagerViewModel,
    inProductActivity: (Int?) -> Unit
) {
    val navController = rememberNavController()

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Column{
            NavHost(
                navController,
                startDestination = Screen.Menu.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(Screen.Menu.route) {
                    MenuScreen( orderVm = orderViewModel,
                        menuViewModel = menuViewModel)
                }

                composable(Screen.Cart.route) {
                    CartScreen(
                        onCreateOrder = {
                            inFinishOrder()
                            navController.popBackStack()
                        },
                        orderVm = orderViewModel
                    )
                }

                composable(Screen.Account.route)
                {
                    ProfileScreen(
                        logOut = { onLogin() },
                        inAboutScreen = {
                            navController.navigate("about")
                        },
                        loginVm = loginVm,
                        inAdminPanelScreen = {
                            navController.navigate("admin")
                        },
                        inAccountPage = {
                            navController.navigate("accountSettings")
                        },
                        inSettings = {
                            navController.navigate("settings")
                        },
                        inSupport = {
                            navController.navigate("support")
                        },
                        inTestScreen = {
                            navController.navigate("testing")
                        }
                    )
                }
                composable(Screen.Orders.route){ UserOrdersScreen(menuViewModel = menuViewModel) }

                composable(Screen.About.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ){ AboutAppScreen( onBackPressed = { navController.popBackStack() }) }

                composable(Screen.Admin.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ){ AdminPanelScreen(
                    onBackPressed = { navController.popBackStack() },
                    menuVm = menuViewModel,
                    managerVm = managerVm,
                    inProductActivity = {
                        inProductActivity(it)
                    }
                )
                }

                composable(Screen.AccountSettings.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ){
                    AccountScreen(
                        onBackPressed = { navController.popBackStack() },
                        loginVm = loginVm
                    )
                }

                composable(Screen.Settings.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ) {
                    SettingsScreen(
                        onBackPressed = { navController.popBackStack() },
                    )
                }

                composable(Screen.Support.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ) {
                    SupportScreen(
                        onBackPressed = { navController.popBackStack() },
                        inFaqs = { navController.navigate("faqs")},
                        sendTicket = { navController.navigate("errorSend") }
                    )
                }

                composable(Screen.Faqs.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ) {
                    Faqs(
                        onBackPressed = { navController.popBackStack() }
                    )
                }

                composable(Screen.Testing.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ) {}

                composable(Screen.ErrorSend.route,
                    enterTransition = { slideInHorizontally(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)) },
                    exitTransition = { slideOutHorizontally(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500))}
                ) {
                    ErrorTicketSendScreen(
                        onBackPressed = { navController.popBackStack() },
                        loginVm = loginVm
                    )
                }
            }
            BottomNavigationBar(navController = navController, orderVm = orderViewModel)
        }
    })
}

@Composable
fun BottomNavigationBar(navController: NavController, orderVm: OrderViewModel) {
    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        NavigationBar(
            containerColor = colorScheme.background,
            modifier = Modifier
                .shadow(20.dp, ambientColor = colorScheme.onBackground)
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            val cartItemsCount = orderVm.itemsCount.collectAsState()

            NavBarItems.BarItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        if (navItem.route == "cart") {
                            BadgedBox(
                                badge = {
                                    if (cartItemsCount.value > 0) {
                                        Badge(
                                            containerColor = colorScheme.primaryContainer,
                                            contentColor = colorScheme.onPrimaryContainer
                                        ) {
                                            Text("${cartItemsCount.value}")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = navItem.image,
                                    contentDescription = "Local description",
                                )
                            }
                        }
                        else {
                            Icon(
                                imageVector = navItem.image,
                                contentDescription = "Local description",
                            )
                        }
                    },
                    label = {
                        Text(
                            text = navItem.name,
                            style = Typography.titleSmall
                        )
                    },
                    modifier = Modifier
                        .background(colorScheme.background),
                    colors = NavigationBarItemColors(
                        selectedIconColor = colorScheme.onPrimary,
                        unselectedIconColor = colorScheme.onBackground,
                        selectedIndicatorColor = colorScheme.primary,
                        unselectedTextColor = colorScheme.onBackground,
                        selectedTextColor = colorScheme.primary,
                        disabledIconColor = colorScheme.onBackground,
                        disabledTextColor = Color.White
                    )
                )
            }
        }
    })
}

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            image = Icons.Filled.Fastfood,
            route = "menu",
            name = "Меню"
        ),
        BarItem(
            image = Icons.Filled.ShoppingCart,
            route = "cart",
            name = "Корзина"
        ),
        BarItem(
            image = Icons.Filled.History,
            route = "orders",
            name = "Заказы"
        ),
        BarItem(
            image = Icons.Filled.AccountCircle,
            route = "account",
            name = "Профиль"
        )

    )
}

data class BarItem(
    val image: ImageVector,
    val route: String,
    val name: String
)

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Cart : Screen("cart")
    object Orders : Screen("orders")
    object Account : Screen("account")
    object AccountSettings : Screen("accountSettings")
    object About : Screen("about")
    object Admin : Screen("admin")
    object Settings : Screen("settings")
    object Support : Screen("support")
    object Splash : Screen("splashScreen")
    object Faqs: Screen("faqs")
    object Testing: Screen("testing")
    object ErrorSend: Screen("errorSend")
}
