package com.example.coffeevibe.ui.ui.other

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.coffeevibe.R

@Composable
fun AssistChipMenu(
    name: String,
    click: (String) -> Unit,
) {
    AssistChip(
        onClick = {
            click(name)
        },
        label = {
            Text(
                name,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                color = colorScheme.onBackground,
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = colorScheme.surface,
            labelColor = colorScheme.onBackground,
        )
    )
}