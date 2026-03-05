package com.example.coffeevibe.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffeevibe.navigation.Routes.MainBottomRoute.toBottomNavigation
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.viewmodel.LoginViewModel
import com.example.coffeevibe.viewmodel.ManagerViewModel
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel

@Composable
fun BottomNavigationRoute() {
    BottomNavigationUI()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationUI() {
    val navController = rememberNavController()

    val navItems = listOf(
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            route = Routes.Home.route,
            badgeCount = 0,
        ),

        BottomNavItem(
            icon = Icons.Default.Search,
            label = "Search",
            route = Routes.Search.route,
            badgeCount = 12,
        ),

        BottomNavItem(
            icon = Icons.Default.Face,
            label = "Profile",
            route = Routes.Profile.route,
            badgeCount = 0,
        ),

        BottomNavItem(
            icon = Icons.Default.Settings,
            label = "Settings",
            route = Routes.Settings.route,
            badgeCount = 0,
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBarUI(
                navController, navItems
            )
        }) {
        MainNavigation(navController = navController)
    }
}

@Composable
fun NavigationBarUI(
    navController: NavController, navItems: List<BottomNavItem>
) {
    var currentScreen by remember { mutableStateOf(navItems[0].route) }

    NavigationBar(
        containerColor = Color.Transparent,
        contentColor = Color.White,
        tonalElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = Color.Black,
                shape = Shapes.extraLarge
            )
    ) {
        navItems.forEach { bottomNavItem ->
            NavigationBarItem(
                selected = currentScreen == bottomNavItem.route,
                onClick = {
                    currentScreen = bottomNavItem.route
                    navController.toBottomNavigation(bottomNavItem.route)
                },
                label = {
                    Text(text = bottomNavItem.label)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (bottomNavItem.badgeCount > 0) {
                                Badge {
                                    Text(text = bottomNavItem.badgeCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = bottomNavItem.icon,
                            contentDescription = "Image"
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.4f),
                    unselectedTextColor = Color.White.copy(alpha = 0.4f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}