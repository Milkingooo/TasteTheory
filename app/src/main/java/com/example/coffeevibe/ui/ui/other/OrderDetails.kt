package com.example.coffeevibe.ui.ui.other

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.model.Location
import com.example.coffeevibe.ui.ui.OrderNumber
import java.sql.Date
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonSingleSelectSample(
    segments: List<String>,
    actions: (Int) -> Unit,
    title: String
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            color = colorScheme.onBackground,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
        )

        Spacer(modifier = Modifier.weight(1f))

        SingleChoiceSegmentedButtonRow {
            segments.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = segments.size),
                    onClick = {
                        selectedIndex = index
                        actions(selectedIndex)
                    },
                    selected = index == selectedIndex,
                    colors = SegmentedButtonDefaults.colors(colorScheme.primary)
                ) {
                    Text(label)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IndeterminateCircularIndicator() {
    CircularWavyProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = colorScheme.secondary,
        trackColor = colorScheme.surfaceVariant
    )
}

@SuppressLint("SimpleDateFormat")
@Composable
fun UserOrderItem(
    number: String,
    price: Int,
    pickupTime: String,
    state: String
) {
    val parts = pickupTime.split("=") // Разбиваем строку на части
    val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
    val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
    val milliseconds = seconds * 1000 + nanoseconds / 1_000_000
    val date = Date(milliseconds)
    val format = SimpleDateFormat("HH:mm")
    val timeString = format.format(date)

    OutlinedCard(
        onClick = { /* Do something */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.background),
        shape = RoundedCornerShape(16.dp),

    ) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Text(
                    text = "К оплате: $price₽",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 18.sp
                )
                Text(
                    text = "Заберите к $timeString",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 16.sp
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state,
                    color = if (state == "Готов") colorScheme.secondary else colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 20.sp
                )

                Text(
                    text = number,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun BaseButton(
    click: () -> Unit,
    title: String,
    color: ButtonColors
){
    Button(
        onClick = { click() },
        colors = color,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(52.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                color = colorScheme.background,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
            )
        )
    }
}

@Composable
fun TextFieldWithName(
    title: String,
    value: String,
    exitValue: (String) -> Unit,
    isInCorrect: Boolean,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
){
    Column(
        modifier = Modifier.padding(8.dp),
    ) {
        OutlinedTextField(
            value = value,
            label = { Text(text = title,
                fontSize = 16.sp,
                color = colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))) },
            onValueChange = {
                exitValue(it)
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.onBackground,
                unfocusedBorderColor = colorScheme.onSurface,
                unfocusedPlaceholderColor = colorScheme.onBackground,
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = keyboardType),
            placeholder = { Text(placeholder, color = colorScheme.onSurface) },
            isError = isInCorrect,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SwitchWithThumbIconSample(
    title: String
) {
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            color = colorScheme.onBackground,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            modifier = Modifier.semantics { contentDescription = "Demo with icon" },
            checked = checked,
            onCheckedChange = { checked = it },
            thumbContent = {
                if (checked) {
                    // Icon isn't focusable, no need for content description
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            },
        )
    }
}

@Composable
fun BaseTextBlockWithBackground(
    text: String
) {

    Card(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                color = colorScheme.background,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun UserOrder(
    price: Int,
    number: String,
    dateOrder: String,
) {
    val parts = dateOrder.split("=") // Разбиваем строку на части
    val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
    val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
    val milliseconds = seconds * 1000 + nanoseconds / 1_000_000
    val date = Date(milliseconds)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val format2 = SimpleDateFormat("EEE, dd MMM yyyy HH:mm")
    val timeString = format2.format(date)

    OutlinedCard(
        onClick = { /* Do something */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.background),
        shape = RoundedCornerShape(16.dp),

        ) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Text(
                    text = timeString,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 18.sp
                )
                Text(
                    text = "Итого: $price₽",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 16.sp
                )
            }
                Text(
                    text = number,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 20.sp
                )
        }
    }
}

@Composable
fun CartItemNew(
    name: String,
    price: Int,
    image: String,
    quantity: Int,
    onPlus: () -> Unit,
    onMinus: () -> Unit
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
                    text = "$price ₽",
                    color = colorScheme.onBackground,
                    fontSize = 16.sp)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuantityControl(
                        onMinus = onMinus,
                        quantity = quantity,
                        onPlus = onPlus
                    )

                    Spacer(modifier = Modifier.weight(1f))

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
}

@Composable
fun QuantityControl(
    onMinus: () -> Unit,
    quantity: Int,
    onPlus: () -> Unit
){
    var scale by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(quantity) {
        scale = 1.2f

        animate(
            initialValue = 1.2f,
            targetValue = 1f,
            animationSpec = tween(150)
        ) { value, _ ->
            scale = value
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(colorScheme.surface, RoundedCornerShape(8.dp))
            .height(35.dp)
    ) {
        IconButton(onClick = {
            onMinus()
        }) {
            if (quantity > 1) {
                Icon(
                    Icons.Filled.Remove,
                    contentDescription = "Localized description",
                    tint = colorScheme.onBackground,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .animateContentSize()
                )
            }
            else {
                Icon(
                    Icons.Filled.DeleteOutline,
                    contentDescription = "Localized description",
                    tint = colorScheme.onBackground,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .animateContentSize()
                )
            }
        }

        Text(
            text = "$quantity шт",
            color = colorScheme.onBackground,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
            modifier = Modifier.scale(scale),
            fontSize = 14.sp
        )

        IconButton(onClick = {
            onPlus()
        }) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Localized description",
                tint = colorScheme.onBackground,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        }
    }
}

@Composable
fun OrderBottomBar(
    totalPrice: Int,
    orderAvailable: Boolean,
    onCreateOrder: () -> Unit
) {
    val navigationBars = WindowInsets.navigationBars.asPaddingValues()

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
//            .padding(bottom = navigationBars.calculateBottomPadding())
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        Button(
            onClick = { onCreateOrder() },
            enabled = orderAvailable,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Filled.Payments,
                    contentDescription = null
                )
                Text(
                    text = "К оформлению",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$totalPrice₽",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun YandexCheckoutBar(
    totalPrice: Int,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val navPadding = WindowInsets.navigationBars
        .asPaddingValues()
        .calculateBottomPadding()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
//                bottom = navPadding + 8.dp,
                start = 12.dp,
                end = 12.dp
            )
            .clip(RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Левая часть – итог
            Text(
                text = "Итого: $totalPrice ₽",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Правая часть – кнопка
            Button(
                onClick = onClick,
                enabled = enabled,
                modifier = Modifier.height(44.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "К оплате",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

