package com.example.coffeevibe.ui.ui.other

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.customUi.BoxDropdownMenuItem
import com.example.coffeevibe.ui.ui.customUi.MaterialList
import com.example.coffeevibe.ui.ui.customUi.MaterialListItem
import com.example.coffeevibe.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    logOut: () -> Unit,
    inAboutScreen: () -> Unit,
    inTestScreen: () -> Unit,
    inAccountPage: () -> Unit,
    inAdminPanelScreen: () -> Unit,
    inSettings: () -> Unit,
    inSupport: () -> Unit,
    loginVm: LoginViewModel
) {
    val name by loginVm.username.collectAsState()
    val userRole by loginVm.userRole.collectAsState()
    val isUserLoggedIn = loginVm.isUserLogin.collectAsState()

    CoffeeVibeTheme(context2 = LocalContext.current, content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        if (isUserLoggedIn.value) {
                            Text(
                                text = name,
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 28.sp,
                                textAlign = TextAlign.Left
                            )
                        } else {
                            Text(
                                text = "Профиль",
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 28.sp,
                                textAlign = TextAlign.Left
                            )
                        }
                    },
                    windowInsets = TopAppBarDefaults.windowInsets,
                    colors=TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(paddingValues)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                // Имя
//                Text(
//                    text = name,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = colorScheme.onBackground,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
                Spacer(modifier = Modifier.height(24.dp))

                val tabsWithoutLogin = listOf(
                    MaterialListItem(
                        title = "Приложение",
                        subtitle = "Тема, язык",
                        action = {
                            inSettings()
                        },
                        icon = Icons.Filled.Settings,
                        iconTint = colorScheme.secondaryContainer,
                        iconBackground = colorScheme.primary
                    ),
                    MaterialListItem(
                        title = "Поддержка",
                        subtitle = "Обратная связь, FAQ",
                        action = {
                            inSupport()
                        },
                        icon = Icons.Filled.SupportAgent,
                        iconTint = colorScheme.secondaryContainer,
                        iconBackground = colorScheme.primary
                    )
                )

                val adminTabs = listOf(
                    MaterialListItem(
                        title = "Администрирование",
                        subtitle = "Управление заказами, склад, реклама, статистика",
                        action = {
                            inAdminPanelScreen()
                        },
                        icon = Icons.Filled.AdminPanelSettings,
                        iconTint = colorScheme.secondaryContainer,
                        iconBackground = colorScheme.primary
                    )
                    ,
                    MaterialListItem(
                        title = "Тестирование",
                        subtitle = "",
                        action = {
                            inTestScreen()
                        },
                        icon = Icons.Filled.Terminal,
                        iconTint = colorScheme.secondaryContainer,
                        iconBackground = colorScheme.error
                    )
                )

                val tabs = mutableListOf<MaterialListItem>()
                if (isUserLoggedIn.value) {
                    tabs.add(
                        MaterialListItem(
                            title = "Аккаунт",
                            subtitle = "Настройки аккаунта, сброс пароля",
                            action = {
                                inAccountPage()
                            },
                            icon = Icons.Filled.AccountCircle,
                            iconTint = colorScheme.secondaryContainer,
                            iconBackground = colorScheme.primary
                        )
                    )
                }
                tabs.addAll(tabsWithoutLogin)
                if (userRole == 0) {
                    tabs.addAll(adminTabs)
                }

                MaterialList(
                    items = tabs,
                    backgroundColor = colorScheme.secondaryContainer,
                    textColor = colorScheme.onBackground
                )

//                SettingsSubCategory("Аккаунт",
//                    icon = Icons.Filled.AccountCircle,
//                    action = {
//                        inAccountPage()
//                    })
//
//                SettingsSubCategory("Приложение",
//                    icon = Icons.Filled.Settings,
//                    action = {
//                        inSettings()
//                    })
//
//                SettingsSubCategory("Поддержка",
//                    icon = Icons.Filled.SupportAgent,
//                    //BitmapPainter(ImageBitmap.imageResource(R.drawable.settings_48)),
//                    action = {
//                        inSupport()
//                    })
//
//                SettingsSubCategory("Администрирование",
//                    icon = Icons.Filled.AdminPanelSettings,
//                    action = { inAdminPanelScreen() })

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        logOut()
                        loginVm.logout()
                    },
                    colors = ButtonDefaults.buttonColors(colorScheme.error),
                    shape = Shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(16.dp)
                        .height(52.dp)
                ) {
                    Text(
                        text = if(isUserLoggedIn.value) "Выйти" else "Войти",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = colorScheme.background,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                        )
                    )
                }

            }
        }

    })
}


