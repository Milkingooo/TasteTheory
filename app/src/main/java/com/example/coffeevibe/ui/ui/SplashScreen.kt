package com.example.coffeevibe.ui.ui

import android.graphics.Color
import android.util.Size
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.animationSpec
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.viewmodel.MenuViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplashScreen(menuVm: MenuViewModel) {
    val scale = remember {
        Animatable(0f)
    }

        // AnimationEffect
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            menuVm.loadMenu()
            menuVm.loadOrders()
            delay(5000L)
        }
    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        // Image
//    Box(contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()) {
//        ShimmeringText(
//            text = "SINTY",
//            shimmerColor = androidx.compose.ui.graphics.Color(0xFF_46_3F_3A),
//            textStyle = LocalTextStyle.current.copy(
//                fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
//                fontSize = 54.sp,
//                letterSpacing = 5.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//    }
        Box(Modifier.fillMaxSize().systemBarsPadding().padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.volodya),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(scale.value)
                    .clip(CircleShape)
            )
            Text(
                text = "Sinty",
                fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                fontSize = 48.sp,
                letterSpacing = 5.sp,
                modifier = Modifier.align(Alignment.BottomCenter),
                color = colorScheme.primary
            )
        }
    })
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShimmeringText(
    text: String,
    shimmerColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(1000, 500, LinearEasing)
){
    val infiniteTransition = rememberInfiniteTransition(label = "ShimmeringTextTransition")

    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "ShimmerProgress"
    )

    val brush = remember(shimmerProgress) {
        object : ShaderBrush() {
            override fun createShader(size: androidx.compose.ui.geometry.Size): Shader {
                // Определите начальное смещение по оси X, начиная от левого края текста
                val initialXOffset = -size.width
                // Общее расстояние, по которому будет проходить мерцание (удвойте ширину текста для полного покрытия)
                val totalSweepDistance = size.width * 2
                // Рассчитайте текущее положение мерцания на основе прогресса анимации
                val currentPosition = initialXOffset + totalSweepDistance * shimmerProgress

                return LinearGradientShader(
                    colors = listOf(androidx.compose.ui.graphics.Color.Transparent, shimmerColor, androidx.compose.ui.graphics.Color.Transparent) as List<androidx.compose.ui.graphics.Color>,
                    from = Offset(currentPosition, 0f),
                    to = Offset(currentPosition + size.width, 0f)
                )
            }
        }
    }

    Text(
        text = text,
        modifier = modifier,
        style = textStyle.copy(brush = brush),
        color = colorScheme.onBackground
    )
}