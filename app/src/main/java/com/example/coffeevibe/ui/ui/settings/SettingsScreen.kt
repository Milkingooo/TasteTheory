package com.example.coffeevibe.ui.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.customUi.BoxDropdownMenuItem
import com.example.coffeevibe.ui.ui.customUi.DropdownMenuWithName
import com.example.coffeevibe.ui.ui.other.SegmentedButtonSingleSelectSample
import com.example.coffeevibe.ui.ui.other.SettingsSubCategory
import com.example.coffeevibe.utils.ThemeManager
import com.example.coffeevibe.utils.ThemeMode
import com.example.coffeevibe.utils.ThemeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
) {
    val options = listOf("Русский", "English")

    CoffeeVibeTheme(context2 = LocalContext.current, content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Настройки",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 28.sp,
                            textAlign = TextAlign.Left
                        )
                    },
                    actions = {
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
                    },
                    windowInsets = TopAppBarDefaults.windowInsets,
                    colors=TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background)
                ) }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(innerPadding)
            ) {

                //SwitchWithThumbIconSample("Уведомления", actions = {})

                val themeManager = ThemeManager(LocalContext.current)

                val boxes = listOf(
                    BoxDropdownMenuItem(
                        title = "Системная",
                        icon = Icons.Outlined.StarOutline,
                        action = {
                            themeManager.setIsSystemTheme(true)
                            themeManager.setIsDarkTheme(false)
                            themeManager.setIsDynamicTheme(false)
                            ThemeState.currentTheme = ThemeMode.SYSTEM
                        }
                    ),
                    BoxDropdownMenuItem(
                        title = "Светлая",
                        icon = Icons.Outlined.LightMode,
                        action = {
                            themeManager.setIsDarkTheme(false)
                            themeManager.setIsDynamicTheme(false)
                            themeManager.setIsSystemTheme(false)
                            ThemeState.currentTheme = ThemeMode.LIGHT
                        }
                    ),
                    BoxDropdownMenuItem(
                        title = "Тёмная",
                        icon = Icons.Outlined.DarkMode,
                        action = {
                            themeManager.setIsDarkTheme(true)
                            themeManager.setIsSystemTheme(false)
                            themeManager.setIsDynamicTheme(false)
                            ThemeState.currentTheme = ThemeMode.DARK
                        }
                    ),
                    BoxDropdownMenuItem(
                        title = "Динамичная",
                        icon = Icons.Outlined.ColorLens,
                        action = {
                            themeManager.setIsDynamicTheme(true)
                            themeManager.setIsDarkTheme(false)
                            themeManager.setIsSystemTheme(false)
                            ThemeState.currentTheme = ThemeMode.DYNAMIC
                        }
                    ),
                )
                DropdownMenuWithName(
                    title = "Тема",
                    current = when {
                        themeManager.isDarkTheme() -> "Тёмная"
                        themeManager.isSystemTheme() -> "Системная"
                        themeManager.isDynamicTheme() -> "Динамичная"
                        else -> "Светлая"
                    },
                    items = boxes,
                    isExpanded = false
                )

                SegmentedButtonSingleSelectSample(
                    segments = options,
                    actions = {},
                    title = "Язык"
                )

                HorizontalDivider(
                    modifier = Modifier.padding(8.dp).padding(horizontal = 24.dp),
                )

                Text(
                    text = "Версия 1.0",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                )
            }
        }
    })
}