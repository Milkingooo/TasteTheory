package com.example.coffeevibe.ui.ui.other

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberTooltipState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.model.OrderManagerItem
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.utils.ConvertOrderState
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonSingleSelectSample(
    segments: List<String>,
    actions: (Int) -> Unit,
    title: String,
    orientationHorizontal: Boolean = true,
    inputIndex: Int = 0
) {
    var selectedIndex by remember { mutableIntStateOf(inputIndex) }

    var openDialog by remember { mutableStateOf(false) }
    var confirmAction by remember { mutableStateOf(false) }

//    if (openDialog) {
//        MyAlertDialog(
//            onDismissRequest = { openDialog = false },
//            onConfirmation = {
//                confirmAction = true
//                openDialog = false
//            },
//            dialogTitle = "Подтвердите действие",
//            icon = Icons.Filled.CheckBox,
//            dialogText = "Подтвердить действие?",
//
//        )
//    }

    if (orientationHorizontal) {
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
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = segments.size
                        ),
                        onClick = {
                            //openDialog = true
                            selectedIndex = index
                            actions(selectedIndex)
                            //confirmAction = false
                        },
                        selected = index == selectedIndex,
                        colors = SegmentedButtonDefaults.colors(colorScheme.primary)
                    ) {
                        Text(label, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
    else {
        Column(
            modifier = Modifier.padding(8.dp).horizontalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

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
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = segments.size
                        ),
                        onClick = {
                            openDialog = true

                            //if (confirmAction) {
                                selectedIndex = index
                                actions(selectedIndex)
                                //confirmAction = false
                            //}
                        },
                        selected = index == selectedIndex,
                        colors = SegmentedButtonDefaults.colors(colorScheme.primary)
                    ) {
                        Text(label, overflow = TextOverflow.Ellipsis)
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun UserOrderItem(
    number: String,
    price: Int,
    pickupTime: String,
    state: Int,
    cancelOrder: () -> Unit,
    orderDate: String
) {
    val parts = pickupTime.split("=") // Разбиваем строку на части
    val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
    val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
    val milliseconds = seconds * 1000 + nanoseconds / 1_000_000

    val parts2 = orderDate.split("=") // Разбиваем строку на части
    val seconds2 = parts2[1].split(",")[0].toLong() // Извлекаем секунды
    val nanoseconds2 = parts2[2].split(")")[0].toLong() // Извлекаем наносекунды
    val milliseconds2 = seconds2 * 1000 + nanoseconds2 / 1_000_000

    val date = Date(milliseconds)
    val format = SimpleDateFormat("HH:mm")
    val timeString = format.format(date)

    val threeMinutesPlus = milliseconds2 + 180000
    val cancelTime = format.format(Date(threeMinutesPlus))

    var orderActionsOpen by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    var confirmAction by remember { mutableStateOf(false) }

    if (openDialog) {
        MyAlertDialog(
            onDismissRequest = { openDialog = false },
            onConfirmation = {
                confirmAction = true
                openDialog = false
                cancelOrder()
            },
            dialogTitle = "Подтвердите действие",
            icon = Icons.Filled.CheckBox,
            dialogText = "Вы уверены, что хотите отменить заказ?"
        )
    }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        // Icon button should have a tooltip associated with it for a11y.
        TooltipBox(
            positionProvider =
                TooltipDefaults.rememberTooltipPositionProvider(
                    TooltipAnchorPosition.Above),
            tooltip = { PlainTooltip { Text("Ваш заказ") } },
            state = rememberTooltipState(),
        ) {
            OutlinedCard(
                onClick = {
                    orderActionsOpen = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(102.dp)
                    .shadow(4.dp, Shapes.medium, spotColor = colorScheme.onSurface)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                shape = Shapes.medium,

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
                            text = "Ваш заказ",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 18.sp
                        )
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
                        if (isWithinThreeMinutes(orderDate)) {
                            Text(
                                text = "Можно отменить до $cancelTime",
                                color = colorScheme.error,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 12.sp
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = ConvertOrderState.convertOrderStateToString(state),
                            color = if (state == 3) colorScheme.secondary else colorScheme.onBackground,
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

        Spacer(modifier = Modifier.height(4.dp))
        if (isWithinThreeMinutes(orderDate)) {
            DropdownMenu(
                expanded = orderActionsOpen,
                onDismissRequest = { orderActionsOpen = false },
                containerColor = colorScheme.surface,
                shape = Shapes.medium
            ) {
                DropdownMenuItem(
                    text = { Text("Отменить заказ", color = colorScheme.error) },
                    onClick = {
                        openDialog = true
                        //if (confirmAction) { cancelOrder() }
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.DeleteOutline,
                            tint = colorScheme.error,
                            contentDescription = null
                        )
                    },
                )
            }
        }
    }
}

fun isWithinThreeMinutes(pickupTimes: String): Boolean {
    // Парсим время из строки
    val parts = pickupTimes.split("=") // Разбиваем строку на части
    if (parts.size != 3) return false // Проверяем наличие всех частей

    try {
        val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
        val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
        val milliseconds = seconds * 1000 + nanoseconds / 1_000_000

        // Получаем текущее время
        val currentMillis = System.currentTimeMillis()

        // Рассчитываем разницу
        val diffInMillis = currentMillis - milliseconds

        // Проверяем, прошла ли меньше 3 минут
        return diffInMillis <= 180_000L
    } catch (e: NumberFormatException) {
        Log.e("OrderTimeParsing", "Ошибка парсинга времени: ${e.message}")
        return false
    }
}


@Composable
fun KbjuField(
    kbju: String
) {
    val parts = kbju.split(";") // Разбиваем строку на части
    val calories = parts[0] // Извлекаем калории
    val protein = parts[1] // Извлекаем белки
    val fat = parts[2] // Извлекаем жиры
    val carbohydrates = parts[3] // Извлекаем углеводы

    OutlinedCard(
        onClick = { /* Do something */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.background),
        shape = RoundedCornerShape(16.dp),

        ) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = calories,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 16.sp
                )
                Text(
                    text = "ккал",
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 14.sp
                )
            }

            VerticalDivider(color = colorScheme.onBackground)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = protein,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 16.sp
                )
                Text(
                    text = "белки",
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 14.sp
                )
            }

            VerticalDivider(color = colorScheme.onBackground, thickness = 1.dp)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = fat,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 16.sp
                )
                Text(
                    text = "жиры",
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 14.sp
                )
            }

            VerticalDivider(color = colorScheme.onBackground, thickness = 1.dp)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = carbohydrates,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 16.sp
                )
                Text(
                    text = "углеводы",
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun BaseButtonWithIcon(
    click: () -> Unit,
    icon: ImageVector,
    iconTint: Color,
    color: IconButtonColors,
    modifier: Modifier,
    shape: androidx.compose.ui.graphics.Shape,
){
//    Button(
//        onClick = { click() },
//        colors = color,
//        shape = shape,
//        modifier = modifier
//    ) {
//        Icon(
//            icon,
//            tint = iconTint,
//            contentDescription = null
//        )
//    }
    IconButton(
        onClick = { click() },
        colors = color,
        shape = shape,
        modifier = modifier
    ) {
        Icon(
            icon,
            tint = iconTint,
            contentDescription = null
        )
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
        shape = Shapes.medium,
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
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier,
    enabled: Boolean = false
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
            modifier = modifier,
            enabled = enabled
        )
    }
}

@Composable
fun SwitchWithThumbIconSample(
    title: String,
    isChecked: Boolean = false,
    actions: () -> Unit
) {
    var checked by remember { mutableStateOf(isChecked) }

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
            onCheckedChange = {
                checked = it
                actions() },
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
    state: Int
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
        border = BorderStroke(1.dp,
            if (state == 5) colorScheme.error else colorScheme.secondary
        ))
    {
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
fun MyAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon", tint = colorScheme.onBackground)
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Подтвердить")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Отмена")
            }
        },
        containerColor = colorScheme.background,
        textContentColor = colorScheme.onBackground,
        titleContentColor = colorScheme.onBackground
    )
}

@Composable
fun ManagerOrdersListItem(
    order: OrderManagerItem,
    onUpdate: (Int) -> Unit,
    state: Int
) {
    val parts = order.pickupTime.toString().split("=") // Разбиваем строку на части
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
            .wrapContentHeight()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),

        ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(
                        text = "Заберут к  $timeString",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "К оплате: ${order.totalPrice}₽",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = order.id.toString(),
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 36.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val buttons = listOf("Создан", "Готовится", "Готов", "Выдан", "Отменен")
            SegmentedButtonSingleSelectSample(
                title = "Выберите действие",
                segments = buttons,
                actions = {
                        onUpdate(
                            when (it) {
                                0 -> 1
                                1 -> 2
                                2 -> 3
                                3 -> 4
                                4 -> 5
                                else -> 1
                            }
                        )
                },
                orientationHorizontal = false,
                inputIndex = state - 1
            )

            Spacer(modifier = Modifier.height(12.dp))

//            Log.d("OrderDetails", "Order items: ${order.orderItems}")
//            //[]

            order.orderItems.forEach { item ->
                    ListItem(
                        headlineContent = {
                            Text(
                                text = item.name,
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 16.sp
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Количество: ${item.quantity}",
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 16.sp
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Filled.Receipt,
                                contentDescription = "Localized description",
                                tint = colorScheme.onBackground
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clip(Shapes.small),
                        colors = ListItemDefaults.colors(containerColor = colorScheme.surface),
                    )
                    HorizontalDivider(modifier = Modifier.padding(10.dp))
                }
             }
    }
}
