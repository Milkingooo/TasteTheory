package com.example.coffeevibe.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: String,
    val badgeCount: Int,
)
