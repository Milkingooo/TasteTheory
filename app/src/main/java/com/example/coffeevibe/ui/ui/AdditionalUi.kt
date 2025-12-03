package com.example.coffeevibe.ui.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.database.CartEntity
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.utils.AuthUtils
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderFinishViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import java.sql.Date
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinimalDialogFinish(
    state: Boolean = false,
    onClose: (Boolean) -> Unit,
    address: String,
    totalPrice: Int,
    items: List<CartEntity>,
    pickupTime: String,
    continueOrder: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(true)
    var showBottomSheet by remember { mutableStateOf(state) }
    val progressState by remember { mutableStateOf(false) }

//    val parts = pickupTime.split("=") // Разбиваем строку на части
//    val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
//    val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
//    val milliseconds = seconds * 1000 + nanoseconds / 1_000_000
//    val date = Date(milliseconds)
//    val format2 = SimpleDateFormat("HH:mm")
//    val timeString = format2.format(date)

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
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Подтверждение",
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                    {
                        items(items, key = { it.id }) {
                            Text(
                                text = """${it.name}
                                    |${it.price}₽ x ${it.quantity} 
                                    |${it.price * it.quantity}₽
                                """.trimMargin(),
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))

//                            Text(
//                                text = "Место получения: $address",
//                                color = colorScheme.onBackground,
//                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
//                                fontSize = 16.sp,
//                                modifier = Modifier.fillMaxWidth()
//                            )
//
//                            Spacer(modifier = Modifier.height(3.dp))
//
//                            Text(
//                                text = "Время получения: $pickupTime",
//                                color = colorScheme.onBackground,
//                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
//                                fontSize = 16.sp,
//                                modifier = Modifier.fillMaxWidth()
//                            )
//
//                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "Оплата при получении",
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "Итоговая стоимость: $totalPrice₽",
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        onClose(showBottomSheet)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorScheme.background
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(52.dp)
                                ) {
                                    Text(
                                        "Назад",
                                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                        color = colorScheme.primary,
                                        fontSize = 18.sp
                                    )
                                }

                                Button(
                                    onClick = {
                                        continueOrder()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorScheme.primary,
                                        contentColor = colorScheme.onBackground
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(52.dp)
                                ) {
                                    if (progressState) {
                                        CircularProgressIndicator(
                                            color = colorScheme.background,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    } else {
                                        Text(
                                            "Подтвердить",
                                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                            color = colorScheme.background,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    })
}

@Composable
fun MinimalDialog(onDismissRequest: () -> Unit) {
    CoffeeVibeTheme(content = {
        Dialog(onDismissRequest = { onDismissRequest() }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = "name",
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "description",
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()))
                }
            }
        }
    })
}