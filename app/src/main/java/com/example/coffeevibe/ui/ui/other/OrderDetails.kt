package com.example.coffeevibe.ui.ui.other

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun IndeterminateCircularIndicator() {
    var loading by remember { mutableStateOf(false) }

    Button(onClick = { loading = true }, enabled = !loading) {
        Text("Start loading")
    }

    if (!loading) return

    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = colorScheme.secondary,
        trackColor = colorScheme.surfaceVariant,
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
    placeholder: String
){
    Column(
        modifier = Modifier.padding(8.dp),
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Left,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            color = colorScheme.onBackground,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = value,
            onValueChange = {
                exitValue(it)
            },
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.onBackground,
                unfocusedBorderColor = colorScheme.onSurface,
                unfocusedPlaceholderColor = colorScheme.onBackground,
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
