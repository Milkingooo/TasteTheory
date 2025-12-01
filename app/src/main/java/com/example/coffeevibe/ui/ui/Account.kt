package com.example.coffeevibe.ui.ui

import android.content.Intent
import android.graphics.drawable.Icon
import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Uri
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.utils.DeviceUtils
import com.example.coffeevibe.viewmodel.LoginViewModel

@Composable
fun AccountScreen(
    logOut: () -> Unit,
    //loadUpdateApk: () -> Unit
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
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(innerPadding)
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

                Text(
                    text = name,
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 28.sp,
                    textAlign = TextAlign.Left
                )

                Spacer(modifier = Modifier.size(26.dp))

//                SettingsSubCategory("Настройки аккаунта",
//                    icon = Icons.Filled.AccountCircle,
//                    action = {})
//                SettingsSubCategory("Поддержка",
//                    icon = Icons.Filled.SupportAgent,
//                    //BitmapPainter(ImageBitmap.imageResource(R.drawable.settings_48)),
//                    action = {})
//                SettingsCategoryWithButton("Текущая версия: ${DeviceUtils.getAppVersion(context)}",
//                    icon = Icons.Filled.Update,
//                    action = {
//
//                    })
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        logOut()
                        loginVm.logout()
                    },
                    colors = ButtonDefaults.buttonColors(colorScheme.primary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(45.dp)
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
fun AccountPreview() {
    AccountScreen(
        logOut = {}
    )
}



@Composable
fun SettingsCategoryWithButton(name: String,
                        action: () -> Unit,
                        icon: ImageVector) {
    CoffeeVibeTheme(content = {
        val colorScheme = MaterialTheme.colorScheme

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = name,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    action()
                },
               shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Обновить",
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Left,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                )
            }
        }
    })
}