package com.example.coffeevibe.ui.ui.adminPanel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.ui.customUi.MaterialList
import com.example.coffeevibe.ui.ui.customUi.MaterialListItem

@Composable
fun HomeAdmin() {
    CoffeeVibeTheme(context2 = LocalContext.current, content = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val tabs = listOf(
                MaterialListItem(
                    title = "Приложение",
                    subtitle = "Ассистент, недавние приложения, приложения по умолчанию",
                    action = {},
                    iconTint = colorScheme.onBackground,
                    iconBackground = colorScheme.primaryContainer
                ),
                MaterialListItem(
                    title = "Уведомления",
                    subtitle = "История уведомлений, разговоры",
                    action = {},
                    iconTint = colorScheme.onBackground,
                    iconBackground = colorScheme.primaryContainer
                ),
                MaterialListItem(
                    title = "Звук и вибрация",
                    subtitle = "Громкость и тактильная обратная связь",
                    action = {},
                    iconTint = colorScheme.onBackground,
                    iconBackground = colorScheme.primaryContainer
                ),
                MaterialListItem(
                    title = "Режимы",
                    subtitle = "Не беспокоить, Ночной режим, Вождение",
                    action = {},
                    iconTint = colorScheme.onBackground,
                    iconBackground = colorScheme.primaryContainer
                ),
                MaterialListItem(
                    title = "Экран и сенсорное управление",
                    subtitle = "",
                    action = {},
                    iconTint = colorScheme.onBackground,
                    iconBackground = colorScheme.primaryContainer
                ),
                MaterialListItem(
                    title = "Обои и стиль",
                    subtitle = "Цвета, тематические значки, сетка приложений",
                    action = {},
                    iconTint = colorScheme.onBackground,
                    iconBackground = colorScheme.primaryContainer
                ),
            )

            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                MaterialList(
                    title = "Настройки приложения",
                    items = tabs,
                    backgroundColor = colorScheme.secondaryContainer,
                    textColor = colorScheme.onBackground
                )
            }
        }
    })
}