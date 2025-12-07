package com.example.coffeevibe.ui.ui.other

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@Composable
fun SettingsSubCategory(name: String,
                        action: () -> Unit,
                        icon: ImageVector
) {
    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        val colorScheme = MaterialTheme.colorScheme

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    action()
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                icon,
                contentDescription = "Reset1",
                modifier = Modifier.size(28.dp),
                tint = colorScheme.onBackground
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = name,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
            )
        }
//        HorizontalDivider()
    })
}