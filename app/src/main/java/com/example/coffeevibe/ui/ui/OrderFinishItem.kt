package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            .background(colorScheme.background),
        colors = CardDefaults.cardColors(containerColor = colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
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
                    text = "$price ₽ x $quantity шт --> ${price * quantity} ₽",
                    color = colorScheme.onBackground,
                    fontSize = 16.sp)

//                Text(
//                    text = "${price * quantity} ₽",
//                    color = colorScheme.onBackground,
//                    //modifier = Modifier.width(150.dp),
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
//                    )
                }
        }
    }
}