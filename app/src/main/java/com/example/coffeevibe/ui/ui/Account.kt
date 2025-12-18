package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.other.BaseButton
import com.example.coffeevibe.ui.ui.other.BaseButtonWithIcon
import com.example.coffeevibe.ui.ui.other.SwitchWithThumbIconSample
import com.example.coffeevibe.ui.ui.other.TextFieldWithName
import com.example.coffeevibe.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onBackPressed: () -> Unit,
    loginVm: LoginViewModel
) {
    var name by remember { mutableStateOf("") }
    var newName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }

    val isInCorrectName by remember { mutableStateOf(false) }
    var isNameEdit by remember { mutableStateOf(false) }
    var isEmailEdit by remember { mutableStateOf(false) }
    val isInCorrectEmail by remember { mutableStateOf(false) }

    var isUserLoggedIn by remember { mutableStateOf(false) }
    isUserLoggedIn = loginVm.isLogin()

    loginVm.giveUserNameEmail { nameDb, emailDb ->
        name = nameDb
        email = emailDb
    }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Аккаунт",
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
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
                TextFieldWithName(
                    title = "Имя",
                    value = name,
                    exitValue = {
                        name = it
                    },
                    isInCorrect = isInCorrectName,
                    placeholder = "Ваше имя",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true
                )
//                    BaseButtonWithIcon(
//                        click = {
//                            isNameEdit = !isNameEdit
//                        },
//                        icon = if (isNameEdit) Icons.Default.Save else Icons.Default.Edit,
//                        iconTint = colorScheme.background,
//                        color = IconButtonColors(
//                            containerColor = colorScheme.primary,
//                            contentColor = colorScheme.background,
//                            disabledContainerColor = colorScheme.primary,
//                            disabledContentColor = colorScheme.background
//                        ),
//                        modifier = Modifier.size(48.dp),
//                        shape = Shapes.small,
//                    )
//                }

                    TextFieldWithName(
                        title = "Почта",
                        value = email,
                        exitValue = {
                            email = it
                        },
                        isInCorrect = isInCorrectEmail,
                        placeholder = "Ваша почта",
                        keyboardType = KeyboardType.Email,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true
                    )

                BaseButton(
                    title = "Сбросить пароль",
                    click = {
                        loginVm.sendPasswordResetEmail(email)
                    },
                    color = ButtonDefaults.buttonColors(colorScheme.primary),
                )

//                BaseButton(
//                    title = "Удалить аккаунт",
//                    click = {
//
//                    },
//                    color = ButtonDefaults.buttonColors(colorScheme.error)
//                )
            }
        }
    })
}