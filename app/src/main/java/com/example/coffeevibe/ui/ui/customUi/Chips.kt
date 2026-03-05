package com.example.coffeevibe.ui.ui.customUi

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.Indicator
import kotlin.text.indexOf

@Composable
fun CategoryRow(
    categories: Map<Int, String>,
    pageCount: Int,
    currentIndex: Int,
    selectedColor: Color = Color.White,
    defaultColor: Color = Color.Gray,
    defaultRadius: Dp = 8.dp,
    selectedLength: Dp = 100.dp,
){
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Row(
        modifier = Modifier
            .padding(top = 4.dp)
            .shadow(
                4.dp,
                Shapes.extraLarge,
                spotColor = colorScheme.secondary
            )
            .fillMaxWidth()
            .clip(Shapes.extraLarge)
            .background(colorScheme.background),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(0.dp),
            pageSpacing = 6.dp,
        ) {
            Indicator2(
                text = categories[it].toString(),
                isSelected = it == currentIndex,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
            )
        }
    }
}

@Composable
fun Indicator2(
    text: String,
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    modifier: Modifier = Modifier.height(defaultRadius)
) {
    val width by animateDpAsState(
        targetValue = if (isSelected) selectedLength else defaultRadius,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

//    Box(
//        modifier = modifier
//            .height(30.dp)
//            .width(width)
//            .clip(Shapes.large)
//            .background(color = if (isSelected) selectedColor else defaultColor)
//    )
    AssistChip(
        onClick = {
            //click(name)
        },
        label = {
            Text(
                "name",
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                color = colorScheme.onBackground,
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isSelected) selectedColor else defaultColor,
            labelColor = colorScheme.onSecondaryContainer,
        ),
        shape = Shapes.extraLarge,
        modifier = Modifier
            .width(width)
            .clip(Shapes.large)
    )
}