package com.example.coffeevibe.ui.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Power
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.customUi.BoxDropdownMenuItem
import com.example.coffeevibe.ui.ui.customUi.DropdownMenuWithName
import com.example.coffeevibe.ui.ui.other.BaseButton
import com.example.coffeevibe.ui.ui.other.TextAreaWithName
import com.example.coffeevibe.ui.ui.other.TextFieldWithName
import com.example.coffeevibe.viewmodel.LoginViewModel
import com.google.firebase.Timestamp

data class Ticket(
    val id: Int,
    val idClient: String,
    val state: String,
    val category: String,
    val email: String,
    val date: String,
    val description: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorTicketSendScreen(
    onBackPressed: () -> Unit,
    loginVm: LoginViewModel
){
    var errorDescription by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var errorCategory by remember { mutableStateOf("Аккаунт") }

    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Сообщить об ошибке",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 28.sp,
                            textAlign = TextAlign.Left,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val boxes = listOf(
                    BoxDropdownMenuItem(
                        title = "Аккаунт",
                        icon = Icons.Outlined.AccountCircle,
                        action = {
                            errorCategory = "Аккаунт"
                        }
                    ),
                    BoxDropdownMenuItem(
                        title = "Производительность и сеть",
                        icon = Icons.Outlined.Power,
                        action = {
                            errorCategory = "Производительность и сеть"
                        }
                    ),
                    BoxDropdownMenuItem(
                        title = "Интерфейс и дизайн",
                        icon = Icons.Outlined.DesignServices,
                        action = {
                            errorCategory = "Интерфейс и дизайн"
                        }
                    ),
                    BoxDropdownMenuItem(
                        title = "Функционал",
                        icon = Icons.Outlined.Settings,
                        action = {
                            errorCategory = "Функционал"
                        }
                    ),
                    BoxDropdownMenuItem(
                        title = "Предложения",
                        icon = Icons.Outlined.Star,
                        action = {
                            errorCategory = "Предложения"
                        }
                    ),
                )

                DropdownMenuWithName(
                    title = "Категория проблемы",
                    current = errorCategory,
                    isExpanded = false,
                    items = boxes
                )

                TextFieldWithName(
                    title = "Почта для связи",
                    value = email,
                    exitValue = {
                        email = it
                    },
                    isInCorrect = false,
                    placeholder = "Почта для связи",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true
                )

                TextAreaWithName(
                    title = "Опишите проблему",
                    value = errorDescription,
                    exitValue = {
                        errorDescription = it
                    },
                    isInCorrect = false,
                    placeholder = "Опишите подробно вашу проблему...",
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true
                )

                BaseButton(
                    title = "Отправить отчёт",
                    color = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary
                    ),
                    click = {
                        if (email.isNotEmpty() && errorCategory.isNotEmpty() && errorDescription.isNotEmpty()){
                            loginVm.addTicketInDb(Ticket(
                                id = 0,
                                idClient = "",
                                state = "Создан",
                                category = errorCategory,
                                email = email,
                                date = Timestamp.now().toString(),
                                description = errorDescription
                            )){
                                if (it) {
                                    email = ""
                                    errorDescription = ""
                                }
                            }
                        }
                    }
                )
            }
        }
    })
}
