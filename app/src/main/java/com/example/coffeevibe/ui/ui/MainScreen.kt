package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SetMeal
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
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.other.ProfileScreen
import com.example.coffeevibe.ui.ui.other.SettingsScreen
import com.example.coffeevibe.ui.ui.other.SupportScreen
import com.example.coffeevibe.ui.ui.other.UserOrdersScreen
import com.example.coffeevibe.viewmodel.LoginViewModel
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel

@Composable
fun MainScreen(
    inFinishOrder: () -> Unit,
    onLogin: () -> Unit,
    menuViewModel: MenuViewModel,
    orderViewModel: OrderViewModel,
    loginVm: LoginViewModel
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
                        menuViewModel = menuViewModel,
                        navController = navController)
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
                    //AccountScreen({ onLogin() })
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
                        }
                    )
                }
                composable(Screen.Orders.route){ UserOrdersScreen(menuViewModel = menuViewModel) }

                composable(Screen.About.route){ AboutAppScreen( onBackPressed = { navController.navigate("account") }) }

                composable(Screen.Admin.route){ AdminPanelScreen( onBackPressed = { navController.navigate("account") }) }

                composable(Screen.AccountSettings.route){
                    AccountScreen( onBackPressed = { navController.navigate("account") }, loginVm = loginVm)
                }

                composable(Screen.Settings.route) {
                    SettingsScreen( onBackPressed = { navController.navigate("account") })
                }

                composable(Screen.Support.route) {
                    SupportScreen( onBackPressed = { navController.navigate("account") })
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
                .shadow(10.dp, spotColor = colorScheme.primaryContainer)
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

                    },
                    modifier = Modifier
                        .background(colorScheme.background),
                    colors = NavigationBarItemColors(
                        selectedIconColor = colorScheme.onPrimary,
                        unselectedIconColor = colorScheme.onBackground,
                        selectedIndicatorColor = colorScheme.primary,
                        unselectedTextColor = Color.White,
                        selectedTextColor = Color.White,
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
            route = "menu"
        ),
        BarItem(
            image = Icons.Filled.ShoppingCart,
            route = "cart"
        ),
        BarItem(
            image = Icons.Filled.History,
            route = "orders"
        ),
        BarItem(
            image = Icons.Filled.AccountCircle,
            route = "account"
        )

    )
}

data class BarItem(
    val image: ImageVector,
    val route: String
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
}
