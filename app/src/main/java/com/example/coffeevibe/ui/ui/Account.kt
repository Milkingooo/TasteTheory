package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.other.BaseButton
import com.example.coffeevibe.ui.ui.other.SwitchWithThumbIconSample
import com.example.coffeevibe.ui.ui.other.TextFieldWithName
import com.example.coffeevibe.viewmodel.LoginViewModel

@Composable
fun AccountScreen(
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val loginVm = LoginViewModel(context)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isInCorrectName by remember { mutableStateOf(false) }
    val isInCorrectEmail by remember { mutableStateOf(false) }
    val isInCorrectPassword by remember { mutableStateOf(false) }

    var isUserLoggedIn by remember { mutableStateOf(false) }
    isUserLoggedIn = loginVm.isLogin()

    loginVm.giveUserNameEmail { nameDb, emailDb ->
        name = nameDb
        email = emailDb
    }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold() { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .consumeWindowInsets(innerPadding)
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
                }

                Spacer(modifier = Modifier.size(16.dp))

                TextFieldWithName(
                    title = "Имя",
                    value = name,
                    exitValue = {
                        name = it
                    },
                    isInCorrect = isInCorrectName,
                    placeholder = "Ваше имя"
                )

                TextFieldWithName(
                    title = "Почта",
                    value = email,
                    exitValue = {
                        email = it
                    },
                    isInCorrect = isInCorrectEmail,
                    placeholder = "Ваша почта",
                    keyboardType = KeyboardType.Email
                )

                TextFieldWithName(
                    title = "Пароль",
                    value = password,
                    exitValue = {
                        password = it
                    },
                    isInCorrect = isInCorrectPassword,
                    placeholder = "*********",
                    keyboardType = KeyboardType.Password
                )

                BaseButton(
                    title = "Сбросить пароль",
                    click = { },
                    color = ButtonDefaults.buttonColors(colorScheme.primary),
                )

//                TextFieldWithName(
//                    title = "Телефон",
//                    value = phone,
//                    exitValue = {
//                        phone = it
//                    },
//                    isInCorrect = isInCorrectPhone,
//                    placeholder = "+7 (800) 555-35-35",
//                    keyboardType = KeyboardType.Phone
//                )
//
//                SwitchWithThumbIconSample(
//                    "Вход по СМС"
//                )

                BaseButton(
                    title = "Сохранить",
                    click = { },
                    color = ButtonDefaults.buttonColors(colorScheme.primary),
                )

                BaseButton(
                    title = "Удалить аккаунт",
                    click = { },
                    color = ButtonDefaults.buttonColors(colorScheme.error)
                )
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun AccountPreview() {
    AccountScreen(
        {}
    )
}
