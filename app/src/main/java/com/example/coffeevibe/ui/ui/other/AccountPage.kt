package com.example.coffeevibe.ui.ui.other

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.viewmodel.LoginViewModel

@Composable
fun ProfileScreen(
    logOut: () -> Unit,
) {
    val context = LocalContext.current
    val loginVm = LoginViewModel(context)
    var name by remember { mutableStateOf("") }
    var isUserLoggedIn by remember { mutableStateOf(false) }
    isUserLoggedIn = loginVm.isLogin()

    loginVm.giveUserName {
        name = it
    }

    CoffeeVibeTheme(content = {
        Scaffold(
//            topBar = {
////                CenterAlignedTopAppBar(
////                    title = { Text("Профиль", fontWeight = FontWeight.Bold) },
////                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
////                        containerColor = colorScheme.onBackground,
////                        titleContentColor = colorScheme.background
////                    )
////                )
//
//            }
        ) { paddingValues ->
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
                        text = "Аккаунт",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Фото профиля
                Image(
                    painter = painterResource(id = R.drawable.volodya),
                    contentDescription = "Фото профиля",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(colorScheme.background)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Имя
                Text(
                    text = name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )

//                Text(
//                    text = "Уровень: $level ☕",
//                    fontSize = 16.sp,
//                    color = colorScheme.onBackground
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Карточка бонусов
//                Card(
//                    colors = CardDefaults.cardColors(colorScheme.onBackground),
//                    shape = RoundedCornerShape(16.dp),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.padding(20.dp)
//                    ) {
//                        Text(
//                            text = "Бонусные баллы",
//                            fontSize = 16.sp,
//                            color = colorScheme.background
//                        )
//                        Text(
//                            text = "$bonusPoints",
//                            fontSize = 32.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = colorScheme.background
//                        )
//                    }
//                }

                Spacer(modifier = Modifier.height(24.dp))

                SettingsSubCategory("Аккаунт",
                    icon = Icons.Filled.AccountCircle,
                    action = {

                    })

                SettingsSubCategory("История заказов",
                    icon = Icons.AutoMirrored.Filled.FormatListBulleted,
                    action = {})
                SettingsSubCategory("Поддержка",
                    icon = Icons.Filled.SupportAgent,
                    //BitmapPainter(ImageBitmap.imageResource(R.drawable.settings_48)),
                    action = {})
                SettingsSubCategory("Настройки",
                    icon = Icons.Filled.Settings,
                    action = {})
                SettingsSubCategory("О приложении",
                    icon = Icons.Filled.Info,
                    action = {})

                Spacer(modifier = Modifier.height(25.dp))

                // Кнопки действий
                Button(
                    onClick = {
                        logOut()
                        loginVm.logout()
                    },
                    colors = ButtonDefaults.buttonColors(colorScheme.error),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(52.dp)
                ) {
                    Text(
                        text = if(isUserLoggedIn) "Выйти" else "Войти",
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

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileScreen({})
}


