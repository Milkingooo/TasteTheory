package com.example.coffeevibe.ui.ui.customUi

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.Shapes
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuWithName(
    title: String,
    current: String,
    isExpanded: Boolean,
    items: List<BoxDropdownMenuItem>
){
    var expanded by remember { mutableStateOf(isExpanded) }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            color = colorScheme.onBackground,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
            TooltipBox(
                positionProvider =
                    TooltipDefaults.rememberTooltipPositionProvider(
                        TooltipAnchorPosition.Above
                    ),
                tooltip = { PlainTooltip { Text("Тема") } },
                state = rememberTooltipState(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.clickable{expanded = !expanded}
                ) {
                    Text(
                        text = current,
                        textAlign = TextAlign.Left,
                        fontSize = 16.sp,
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                    )

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            Icons.Outlined.ArrowDropDown,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier
                                .width(26.dp)
                                .height(26.dp)
                        )
                    }
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = colorScheme.surface,
                shape = Shapes.medium
            ) {
                items.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                it.title,
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                            )
                        },
                        onClick = {
                            it.action()
                            expanded = false },
                        leadingIcon = {
                            it.icon?.let { imageVector ->
                                Icon(
                                    imageVector,
                                    contentDescription = "Localized description",
                                    tint = colorScheme.onBackground,
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

data class BoxDropdownMenuItem(
    val title: String,
    val icon: ImageVector? = null,
    val action: () -> Unit
)