package com.example.coffeevibe.ui.ui.customUi

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.coffeevibe.ui.activities.ui.theme.ui.theme.Typography
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.other.BaseTextBlockWithBackground
import com.google.firebase.annotations.concurrent.Background

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MaterialList(
    title: String = "",
    items: List<MaterialListItem>,
    backgroundColor: Color,
    textColor: Color)
{
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .clip(Shapes.large)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items.forEach {
            Row(
                modifier = Modifier
                    .clip(Shapes.small)
                    .background(color = backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.SegmentTick)
                        it.action()
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                it.icon?.let { imageVector ->
                    Icon(
                        imageVector,
                        contentDescription = "Localized description",
                        tint = it.iconTint,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(it.iconBackground)
                            .size(40.dp)
                            .padding(4.dp)
                    )
                }

                Spacer(Modifier.size(12.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        it.title,
                        style = Typography.bodyLargeEmphasized,
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    if (it.subtitle.isNotBlank()) {
                        Text(
                            it.subtitle,
                            style = Typography.bodyMediumEmphasized,
                            color = textColor,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

data class MaterialListItem(
    val title: String,
    val subtitle: String,
    val action: () -> Unit,
    val icon: ImageVector? = null,
    val iconTint: Color,
    val iconBackground: Color
)


@Composable
fun ColumnWithShapeAndBackground(
    content: @Composable () -> Unit,
    background: Color,
    padding: Dp
) {
    Column(
        modifier = Modifier
            .clip(Shapes.large)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(background)
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnPreview(){
    ColumnWithShapeAndBackground(
        content = {
            SimpleSearchBarSample()
            BaseTextBlockWithBackground(
                """Hi, this is testing maket
                    |You can sent me message in Telegram
                    |@Milkingooo
                """.trimMargin()
            )
        },
        background = colorScheme.primary,
        padding = 16.dp
    )
}