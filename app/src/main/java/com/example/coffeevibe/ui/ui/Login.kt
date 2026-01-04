package com.example.coffeevibe.ui.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.customUi.MyPasswordTextField
import com.example.coffeevibe.ui.ui.customUi.PasswordField
import com.example.coffeevibe.viewmodel.LoginViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    inReg: () -> Unit,
    onLogin: () -> Unit,
    inManager: () -> Unit,
    loginVm: LoginViewModel
) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isInCorrect by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var progressState by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val textFieldState = rememberTextFieldState()

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Добро пожаловать",
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = "Почта",
                    textAlign = TextAlign.Left,
                    fontSize = 18.sp, // Используем стиль текста из темы
                    modifier = Modifier.fillMaxWidth(),
                    color = colorScheme.onBackground, // Цвет текста из темы
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.onBackground,   // Основной цвет для акцентов
                        unfocusedBorderColor = colorScheme.onSurface, // Цвет границ для неактивного состояния
                        unfocusedPlaceholderColor = colorScheme.onBackground,
                        focusedTextColor = colorScheme.onBackground,
                        unfocusedTextColor = colorScheme.onBackground,
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeholder = { Text("xyz@gmail.com", color = colorScheme.onSurface) },
                    isError = isInCorrect,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Login",
                            tint = colorScheme.onBackground
                        )
                    },
                    modifier = Modifier
                        .clip(Shapes.small)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Пароль",
                    textAlign = TextAlign.Left,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
                )

                Spacer(modifier = Modifier.height(10.dp))

                PasswordField(
                    keyboardActions = {
                        focusManager.clearFocus()
                    },
                    isInCorrect = isInCorrect,
                    state = textFieldState
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Забыли пароль?",
                    textAlign = TextAlign.Left,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            MainScope().launch {
                                if (email.isBlank()) {
                                    Toast
                                        .makeText(context, "Введите почту", Toast.LENGTH_SHORT)
                                        .show()
                                } else if (email.isNotBlank() && loginVm.checkEmailInDb(email = email)) {
                                    loginVm.sendPasswordResetEmail(email = email)
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Пользоателя с таким email не существует",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                        },
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (textFieldState.text.toString().trim().length < 6) {
                            isInCorrect = true
                            Toast.makeText(
                                context,
                                "Пароль должен быть больше 6 символов",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            loginVm.login(
                                login = email,
                                password = textFieldState.text.toString().trim(),
                                isLogin = { login, role ->
                                    progressState = true

                                    when {
                                        login && (role == 0) -> {
                                            isInCorrect = false
                                            progressState = false
                                            onLogin()
                                        }
                                        login && (role == 2) -> {
                                            isInCorrect = false
                                            progressState = false
                                            onLogin()
                                        }
                                        login && (role == 1) -> {
                                            isInCorrect = false
                                            progressState = false
                                            inManager()
                                        }
                                        else -> {
                                            isInCorrect = true
                                            progressState = false
                                        }
                                    }
                                }
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onBackground
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    if (progressState) {
                        CircularProgressIndicator(
                            color = colorScheme.background,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            "Войти",
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            color = colorScheme.background,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Впервые? Зарегистрироваться",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            inReg()
                        },
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                )
            }
        }
    })
}


