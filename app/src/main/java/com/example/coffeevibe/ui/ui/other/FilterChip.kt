package com.example.coffeevibe.ui.ui.other

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        }
    )
}