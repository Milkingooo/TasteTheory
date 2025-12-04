package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.ui.other.QuantityControl

@Composable
fun OrderFinishItem(
    name: String,
    price: Int,
    image: String,
    quantity: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .background(colorScheme.background, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = colorScheme.background),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current).data(data = image)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true) // Плавный переход при загрузке нового изображения
                    }).build(),
                contentDescription = null, // Описание для доступности
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
                    .clip(shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = name,
                    color = colorScheme.onBackground,
                    modifier = Modifier.width(150.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

                Text(
                    text = "$price ₽ x $quantity шт",
                    color = colorScheme.onBackground,
                    fontSize = 16.sp)

                Text(
                    text = "${price * quantity} ₽",
                    color = colorScheme.onBackground,
                    //modifier = Modifier.width(150.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
                    )
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderFinishItemPreview() {
    OrderFinishItem("Кофе",
        150,
        "https://coffee.hb.ru-msk.vkcloud-storage.ru/CoffeeGoods/matcha_tea.jpg",
        1)
}