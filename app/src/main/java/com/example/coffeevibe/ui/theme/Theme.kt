package com.example.coffeevibe.ui.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.coffeevibe.utils.ThemeManager

//private val lightColorScheme = lightColorScheme(
//    background = Background,
//    onBackground = TextAndIcons,
//    primary = AccentColor,
//    error = ErrorColor,
//    tertiary = WarningColor,
//    secondary = SuccessColor,
//    surface = InactiveElementBackground,
//    onSurface = ShadowAndBorder,
//    onSurfaceVariant = TooltipBackground
//)

private val darkColorScheme = darkColorScheme(
    background = DarkBackground,
    onBackground = DarkTextAndIcons,
    primary = DarkAccentColor,
    error = DarkErrorColor,
    tertiary = DarkWarningColor,
    secondary = DarkSuccessColor,
    surface = DarkInactiveElementBackground,
    onSurface = DarkShadowAndBorder,
    onSurfaceVariant = DarkTooltipBackground
)

private val lightColorScheme = lightColorScheme(
    background = LightBackground,
    onBackground = LightTextAndIcons,
    primary = LightAccentColor,
    error = LightErrorColor,
    tertiary = LightWarningColor,
    secondary = LightSuccessColor,
    surface = LightInactiveElementBackground,
    onSurface = LightShadowAndBorder,
    onSurfaceVariant = LightTooltipBackground
)

@Composable
fun CoffeeVibeTheme(
    content: @Composable () -> Unit,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    context2: Context
) {
    val themeManager = ThemeManager(context2)

    fun setTheme() : ColorScheme {
        return if (isDarkTheme && themeManager.isDarkTheme()) darkColorScheme
        else if (!isDarkTheme && themeManager.isDarkTheme()) darkColorScheme
        else lightColorScheme
    }

    MaterialTheme(
        colorScheme  = setTheme(),
        typography = Typography,
        shapes = Shapes,
        content = content
    )


}

