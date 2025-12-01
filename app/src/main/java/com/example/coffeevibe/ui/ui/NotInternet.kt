package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@Composable
fun NotInternet(
    onRetryClicked: () -> Unit
) {
    CoffeeVibeTheme(content = {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Icon(
//                Icons.Filled.SignalWifiConnectedNoInternet4,
//                contentDescription = "Localized description",
//                tint = colorScheme.onBackground,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(80.dp)
//            )
//
//            Spacer(modifier = Modifier.size(26.dp))
//
//            Text(
//                text = "No internet connection",
//                color = colorScheme.onBackground,
//                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
//                fontSize = 20.sp,
//                textAlign = TextAlign.Left
//            )
//        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                Icons.Filled.SignalWifiConnectedNoInternet4,
                contentDescription = "Localized description",
                tint = colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No internet connection",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please check your internet connection and restart the app",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

//                Button(
//                    onClick = { onRetryClicked() },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = colorScheme.primary),
//                    modifier = Modifier
//                        .width(150.dp)
//                        .padding(16.dp),
//                    shape = RoundedCornerShape(16.dp),
//                ) {
//                    Text(
//                        text = "Retry",
//                        color = colorScheme.onBackground,
//                        fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
//                        fontSize = 20.sp,
//                        textAlign = TextAlign.Left
//                    )
//                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun NotInternetPreview() {
    NotInternet({})
}