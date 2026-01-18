package com.example.coffeevibe.ui.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.other.BaseButton
import com.example.coffeevibe.ui.ui.other.TextFieldWithName
import com.example.coffeevibe.viewmodel.LoginViewModel
import androidx.compose.runtime.collectAsState
import com.example.coffeevibe.ui.activities.ui.theme.ui.theme.Typography
import com.example.coffeevibe.ui.ui.customUi.PasswordAlertDialog
import com.example.coffeevibe.ui.ui.customUi.PasswordField
import com.example.coffeevibe.ui.ui.customUi.SimpleBottomSheet
import com.example.coffeevibe.ui.ui.other.BaseTextField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AccountScreen(
    onBackPressed: () -> Unit,
    loginVm: LoginViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }

    val isInCorrectName by remember { mutableStateOf(false) }
    val isInCorrectEmail by remember { mutableStateOf(false) }
    var editProfile by remember { mutableStateOf(false) }
    var enterPassword by remember { mutableStateOf(false) }

    loginVm.giveUserNameEmail { nameDb, emailDb ->
        name = nameDb
        email = emailDb
    }


    if (editProfile){
        SimpleBottomSheet(
            state = editProfile,
        ) {
            var newName by remember { mutableStateOf("") }
            var newEmail by remember { mutableStateOf("") }
            val textFieldState = rememberTextFieldState()

            Text(
                text = "Редактирование профиля",
                color = colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            TextFieldWithName(
                title = "Новое имя",
                value = newName,
                exitValue = {
                    newName = it
                },
                isInCorrect = isInCorrectName,
                placeholder = "Введите имя",
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            )

            TextFieldWithName(
                title = "Новая почта",
                value = newEmail,
                exitValue = {
                    newEmail = it
                },
                isInCorrect = isInCorrectName,
                placeholder = "Введите новую почту",
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            )

            PasswordField(
                keyboardActions = {},
                isInCorrect = textFieldState.text.trim().length > 6,
                state = textFieldState,
                enable = true
            )

            BaseButton(
                title = "Сохранить",
                click = {
                    enterPassword = true
                },
                color = ButtonDefaults.buttonColors(colorScheme.secondary),
                enabled = (newName.isNotEmpty() || newEmail.isNotEmpty() || textFieldState.text.trim().isNotEmpty())
            )

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Удалить аккаунт",
                style = Typography.bodySmallEmphasized,
                textAlign = TextAlign.Center,
                color = colorScheme.error
            )

            if (enterPassword) {
                PasswordAlertDialog(
                    onDismissRequest = {
                        enterPassword  = false
                    },
                    onConfirmation = {
                        enterPassword  = false
                        loginVm.updateUserProfile(
                            newEmail = newEmail.trim(),
                            newPassword = textFieldState.text.trim().toString(),
                            newName = newName.trim(),
                            oldPassword = oldPassword
                        )
                        editProfile = false
                    },
                    output = {
                        oldPassword = it
                    }
                )
            }

        }
    }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Аккаунт",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = colorScheme.onBackground,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            editProfile = !editProfile
                        }
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit",
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
                    .padding(8.dp)
            ) {
                TextFieldWithName(
                    title = "Имя",
                    value = name,
                    exitValue = {
                        name = it
                    },
                    isInCorrect = isInCorrectName,
                    placeholder = "Ваше имя",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                )

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
                    enabled = false
                )

                BaseButton(
                    title = "Сбросить пароль",
                    click = {
                        loginVm.sendPasswordResetEmail(email)
                    },
                    color = ButtonDefaults.buttonColors(colorScheme.primary),
                )
            }
        }
    })
}