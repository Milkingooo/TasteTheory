package com.example.coffeevibe.navigation

import androidx.navigation.NavController
import com.example.coffeevibe.ui.ui.Screen

sealed class Routes(val route: String) {
    //for bottom navigation routes
    data object Home : Routes("home")
    data object Search : Routes("search")
    data object Profile : Routes("profile")
    data object Settings : Routes("settings")

    //for navigate to particular bottom navigation
    data object MainBottomRoute : Routes("main") {
        fun NavController.toBottomNavigation(route: String) = navigate(route) {
            popUpTo(graph.id) { inclusive = true }
        }
    }
}