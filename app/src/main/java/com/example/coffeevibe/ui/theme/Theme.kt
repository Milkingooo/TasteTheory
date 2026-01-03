@file:Suppress("DEPRECATION")

package com.example.coffeevibe.ui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.coffeevibe.utils.ThemeManager

private val lightScheme = lightColorScheme(
    primary = primaryLight, //Основной цвет приложения, который определяет общий визуальный стиль.
    onPrimary = onPrimaryLight, //Цвет текста и иконок на элементах основного цвета.
    primaryContainer = primaryContainerLight, //Светлый оттенок основного цвета, используемый для фона контейнеров и карточек.
    onPrimaryContainer = onPrimaryContainerLight, //Цвет текста и иконок на контейнерах основного цвета.

    secondary = secondaryLight, //Дополнительный цвет, который дополняет основной цвет и добавляет разнообразие дизайну.
    onSecondary = onSecondaryLight, //Цвет текста и иконок на элементах вторичного цвета.
    secondaryContainer = secondaryContainerLight, //Светлый оттенок вторичного цвета, используемый для фона контейнеров и карточек.
    onSecondaryContainer = onSecondaryContainerLight, //Цвет текста и иконок на контейнерах вторичного цвета.

    tertiary = tertiaryLight, //Третичный цвет, который используется реже и добавляет акценты в дизайн.
    onTertiary = onTertiaryLight, //Цвет текста и иконок на элементах третичного цвета.
    tertiaryContainer = tertiaryContainerLight, //Светлый оттенок третичного цвета, используемый для фона контейнеров и карточек.
    onTertiaryContainer = onTertiaryContainerLight, //Цвет текста и иконок на контейнерах третичного цвета.

    error = errorLight, //Цвет, используемый для отображения ошибок и предупреждений.
    onError = onErrorLight, //Цвет текста и иконок на элементах ошибочного цвета.
    errorContainer = errorContainerLight, //Фоновое оформление сообщений об ошибках.
    onErrorContainer = onErrorContainerLight, //Цвет текста внутри контейнера ошибки.

    background = backgroundLight, //Общий фон страницы или экрана.
    onBackground = onBackgroundLight, //Цвет текста и иконок на фоновом элементе.

    surface = surfaceLight, //Цвет поверхности основных компонентов, таких как карточки и панели инструментов.
    onSurface = onSurfaceLight, //Цвет текста и иконок на поверхностных компонентах.
    surfaceVariant = surfaceVariantLight, //Вариант цвета поверхности, используемый для границ и разделения поверхностей.
    onSurfaceVariant = onSurfaceVariantLight, //Цвет текста и иконок на поверхностях варианта.

    outline = outlineLight, //Цвет контуров и рамок вокруг элементов.
    outlineVariant = outlineVariantLight, //Альтернативный цвет контуров и рамок.

    scrim = scrimLight, //Полупрозрачный слой, накладываемый поверх содержимого для эффекта затемнения.

    inverseSurface = inverseSurfaceLight, //Обратный цвет поверхности, используемый для контрастных элементов.
    inverseOnSurface = inverseOnSurfaceLight, //Цвет текста и иконок на обратной стороне поверхности.
    inversePrimary = inversePrimaryLight, //Обратная версия основного цвета.

    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Composable
fun CoffeeVibeTheme(
    context2: Context,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, //для палитры системы true
    content: @Composable() () -> Unit
) {
    val themeManager = ThemeManager(context2)
    var dynamicColor by remember { mutableStateOf(dynamicColor) }

    dynamicColor = themeManager.isDynamicTheme()
    val isSystemTheme = themeManager.isSystemTheme()
    val isDarkTheme = themeManager.isDarkTheme()

  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      isSystemTheme -> {
          if (darkTheme) darkScheme else lightScheme
      }
      isDarkTheme -> darkScheme
      else -> lightScheme
  }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

  MaterialTheme(
    colorScheme = colorScheme,
    content = content
  )
}

