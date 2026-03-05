package com.example.coffeevibe.ui.ui.settings

import android.R.attr.enabled
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.toTextFieldBuffer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedToggleButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TonalToggleButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDragHandle
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberRangeSliderState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.RoundedPolygon
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.bumptech.glide.integration.compose.placeholder
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.customUi.PasswordField
import com.example.coffeevibe.ui.ui.other.BaseButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TestScreen(
    onBackPressed: () -> Unit
){
    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        Icon(
                            Icons.Filled.Terminal,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                        )
                    },
                    title = {
                        Text(
                            text = "Тестирование",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 28.sp,
                            textAlign = TextAlign.Left,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                onBackPressed()
                            }
                        ) {
                            Icon(
                                Icons.Filled.ArrowBackIosNew,
                                contentDescription = "Localized description",
                                tint = colorScheme.onBackground,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        }
                    },
                    windowInsets = TopAppBarDefaults.windowInsets,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SwipeToDismissListItems()
                HorizontalCenteredHeroCarouselSample()
                AllShapes()
//                FlowRow(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    MediumToggleCard(MaterialShapes.Arch)
//                    MediumToggleCard(MaterialShapes.Gem)
//                    MediumToggleCard(MaterialShapes.Clover4Leaf)
//                    MediumToggleCard(MaterialShapes.Clover8Leaf)
//                }
            }
        }
    })
}

@Composable
fun SwipeToDismissListItems() {
    val dismissState = rememberSwipeToDismissBoxState()
    var isVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    if (isVisible) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                val color by
                animateColorAsState(
                    targetValue = when (dismissState.targetValue) {
                        SwipeToDismissBoxValue.Settled -> Color.LightGray
                        SwipeToDismissBoxValue.StartToEnd -> Color.Green
                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                    }
                )
                Box(Modifier.fillMaxSize().clip(Shapes.large).background(color))
            },
            onDismiss = { direction ->
                if (direction == SwipeToDismissBoxValue.EndToStart) {
                    isVisible = false
                } else {
                    scope.launch { dismissState.reset() }
                }
            },
        ) {
            OutlinedCard(shape = Shapes.large) {
                ListItem(
                    headlineContent = { Text("Cupcake", color = colorScheme.onBackground) },
                    supportingContent = { Text("Swipe me left or right!", color = colorScheme.onBackground) },
                )
            }
        }
    }
}
@Composable

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun AllShapes() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ProvideTextStyle(MaterialTheme.typography.labelSmall) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(2.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                allMaterialShapes().forEach { (name, polygon) ->
//                    item {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
////                            Text(name, color = colorScheme.onBackground)
////                            Spacer(
////                                modifier =
////                                    Modifier.requiredSize(56.dp)
////                                        .clip(polygon.toShape())
////                                        .background(MaterialTheme.colorScheme.primary)
////                            )
//                            MediumToggleCard(polygon)
//                        }
//                    }
            //}
                    items(allMaterialShapes(), key = { it }) { (_, polygon) ->
                        MediumToggleCard(polygon)
                    }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private fun allMaterialShapes(): List<Pair<String, RoundedPolygon>> {
    return listOf(
        "Circle" to MaterialShapes.Circle,
        "Square" to MaterialShapes.Square,
        "Slanted" to MaterialShapes.Slanted,
        "Arch" to MaterialShapes.Arch,
        "Fan" to MaterialShapes.Fan,
        "Arrow" to MaterialShapes.Arrow,
        "SemiCircle" to MaterialShapes.SemiCircle,
        "Oval" to MaterialShapes.Oval,
        "Pill" to MaterialShapes.Pill,
        "Triangle" to MaterialShapes.Triangle,
        "Diamond" to MaterialShapes.Diamond,
        "ClamShell" to MaterialShapes.ClamShell,
        "Pentagon" to MaterialShapes.Pentagon,
        "Gem" to MaterialShapes.Gem,
        "Sunny" to MaterialShapes.Sunny,
        "VerySunny" to MaterialShapes.VerySunny,
        "Cookie4Sided" to MaterialShapes.Cookie4Sided,
        "Cookie6Sided" to MaterialShapes.Cookie6Sided,
        "Cookie7Sided" to MaterialShapes.Cookie7Sided,
        "Cookie9Sided" to MaterialShapes.Cookie9Sided,
        "Cookie12Sided" to MaterialShapes.Cookie12Sided,
        "Ghostish" to MaterialShapes.Ghostish,
        "Clover4Leaf" to MaterialShapes.Clover4Leaf,
        "Clover8Leaf" to MaterialShapes.Clover8Leaf,
        "Burst" to MaterialShapes.Burst,
        "SoftBurst" to MaterialShapes.SoftBurst,
        "Boom" to MaterialShapes.Boom,
        "SoftBoom" to MaterialShapes.SoftBoom,
        "Flower" to MaterialShapes.Flower,
        "Puffy" to MaterialShapes.Puffy,
        "PuffyDiamond" to MaterialShapes.PuffyDiamond,
        "PixelCircle" to MaterialShapes.PixelCircle,
        "PixelTriangle" to MaterialShapes.PixelTriangle,
        "Bun" to MaterialShapes.Bun,
        "Heart" to MaterialShapes.Heart,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalCenteredHeroCarouselSample() {

    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        @StringRes val contentDescriptionResId: Int,
    )

    val items =
        listOf(
            CarouselItem(0, R.drawable.volodya, R.string.project_id),
            CarouselItem(1, R.drawable.placeholder, R.string.project_id),
            CarouselItem(2, R.drawable.max, R.string.project_id),
            CarouselItem(3, R.drawable.logo, R.string.project_id),
            CarouselItem(4, R.drawable.shevcov, R.string.project_id),
            )
    val state = rememberCarouselState { items.count() }
    val animationScope = rememberCoroutineScope()
    HorizontalCenteredHeroCarousel(
        state = state,
        modifier = Modifier.fillMaxWidth().height(221.dp).padding(horizontal = 24.dp),
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) { i ->
        val item = items[i]
        Image(
            modifier =
                Modifier.fillMaxWidth()
                    .height(205.dp)
                    .maskClip(MaterialTheme.shapes.medium)
                    .clickable(true, "Tap to focus", Role.Image) {
                        animationScope.launch { state.animateScrollToItem(i) }
                    },
            painter = painterResource(id = item.imageResId),
            contentDescription = stringResource(item.contentDescriptionResId),
            contentScale = ContentScale.Crop,
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediumToggleCard(polygon: RoundedPolygon) {
    var checked by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp)
            .shadow(
                2.dp,
                Shapes.medium,
                spotColor = if (checked) colorScheme.secondaryContainer else colorScheme.secondary
            )
            .clickable {
                checked = !checked
            },
        shape = Shapes.medium,
        border = BorderStroke(
            if (checked) 2.dp else 0.dp,
            if (checked) colorScheme.secondaryContainer else colorScheme.outline
        ),
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .requiredSize(56.dp)
                    .clip(polygon.toShape())
                    .background(color = colorScheme.secondaryContainer),
            )
        }
    }
}