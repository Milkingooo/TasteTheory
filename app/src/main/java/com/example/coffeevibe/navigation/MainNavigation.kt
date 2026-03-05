package com.example.coffeevibe.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun MainNavigation(
    navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen()
        }

        composable(Routes.Search.route) {
            SearchScreen()
        }

        composable(Routes.Profile.route) {
            ProfileScreen()
        }

        composable(Routes.Settings.route) {
            SettingsScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red.copy(alpha = 0.2f))
    ) {
        Text(
            text = "Home Screen",
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red.copy(alpha = 0.2f))
    ) {
        Text(
            text = "Profile Screen",
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red.copy(alpha = 0.2f))
    ) {
        Text(
            text = "Settings Screen",
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SearchScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red.copy(alpha = 0.2f))
    ) {
        Text(
            text = "Search Screen",
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}