package com.example.coffeevibe.ui.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import java.sql.Date
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun OrderNumber(number: String,
                price: Int,
                pickupTime: String) {

    val parts = pickupTime.split("=") // Разбиваем строку на части
    val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
    val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
    val milliseconds = seconds * 1000 + nanoseconds / 1_000_000
    val date = Date(milliseconds)
    val format = SimpleDateFormat("HH:mm")
    val timeString = format.format(date)

    CoffeeVibeTheme(content = {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(116.dp)
                .background(colorScheme.surface, RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column {
                    Text(
                        text = "Номер заказа: $number",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = "К оплате: $price₽",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 20.sp
                )
                Text(
                    text = "Время получения: $timeString",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
    })
}

@Preview(showBackground = true)
@Composable
fun OrderNumPreview() {
    OrderNumber("52", 42, "")
}