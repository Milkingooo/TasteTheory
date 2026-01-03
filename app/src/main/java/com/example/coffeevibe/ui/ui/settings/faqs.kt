@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.example.coffeevibe.ui.ui.settings

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.activities.MainActivity
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Faqs(onBackPressed: () -> Unit
) {
    CoffeeVibeTheme(context2 = LocalContext.current,content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Часто задаваемые вопросы",
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
                    .background(colorScheme.background)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = false // Включаем поддержку JavaScript, если необходимо
                            loadUrl("file:///android_asset/html/faqs.html") // Загружаем HTML-файл из assets папки
                        }
                    },
                    modifier = Modifier.weight(1f),
                    update = {}
                )

            }
        }
    })
}
