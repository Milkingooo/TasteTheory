package com.example.coffeevibe.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightColorScheme = lightColorScheme(
    background = Background,
    onBackground = TextAndIcons,
    primary = AccentColor,
    error = ErrorColor,
    tertiary = WarningColor,
    secondary = SuccessColor,
    surface = InactiveElementBackground,
    onSurface = ShadowAndBorder,
    onSurfaceVariant = TooltipBackground
)

@Composable
fun CoffeeVibeTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme  = lightColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}