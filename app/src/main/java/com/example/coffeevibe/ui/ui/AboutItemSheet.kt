package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutItemSheet(state: Boolean = false,
                   image: String,
                   name: String,
                   description: String,
                   onClose: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(true)
    var showBottomSheet by remember { mutableStateOf(state) }

    CoffeeVibeTheme(content = {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                onClose(showBottomSheet)
            },
            sheetState = sheetState,
            containerColor = colorScheme.background,
        ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter =
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = image)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true) // Плавный переход при загрузке нового изображения
                                }).build()
                        ),
                        contentDescription = null, // Описание для доступности
                        modifier = Modifier
                            .size(250.dp)
                            .clip(shape = RoundedCornerShape(40.dp)),
                        contentScale = ContentScale.Crop,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = name,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = description,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,

                    )

                    Spacer(modifier = Modifier.height(36.dp))
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun AboutItemSheetPreview() {
    AboutItemSheet(true, "","", "", {})
}