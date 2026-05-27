package com.example.coffeevibe.ui.ui.customUi

import android.graphics.drawable.Icon
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.Shapes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SmallTextField(
    height: Dp = 52.dp,
    placeholderText: String = "Placeholder",
    onTextChanged: (String) -> Unit,
    text: String,
    fontSize: TextUnit = 16.sp
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = Modifier
            .background(
                TextFieldDefaults.colors().containerColor(
                    enabled = true,
                    isError = false,
                    focused = false
                ),
                Shapes.large)
            .height(height)
            .fillMaxWidth(),
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        singleLine = true,
        cursorBrush = SolidColor(colorScheme.primary),
        textStyle = LocalTextStyle.current.copy(
            color = colorScheme.onSurface,
            fontSize = fontSize
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                if (text.isNotEmpty()){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    onTextChanged("")
                                    focusManager.clearFocus()
                                }
                        )
                    }
                }

                Box(Modifier.weight(1f)) {
                    if (!WindowInsets.isImeVisible){
                        focusManager.clearFocus()
                    }

                    if (text.isEmpty()) {
                        androidx.wear.compose.material.Text(
                            text = placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = colorScheme.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun MenuItemText(text: String, color: Color,
                 alignment: TextAlign = TextAlign.Start,
                 padding: Dp = 10.dp,
                 maxLines: Int = 1,
                 fontWeight: FontWeight = FontWeight.Normal,
                 fontSize: TextUnit = 16.sp) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        textAlign = alignment,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(padding),
        fontWeight = fontWeight,
        fontSize = fontSize
    )
}

@Composable
fun MyPasswordTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    keyboardActions: KeyboardActions,
    isInCorrect: Boolean
){
    var isVisible by remember { mutableStateOf(false) }

    Box{
        OutlinedTextField(
            value = value,
            onValueChange = { onTextChanged(it) },
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
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isVisible){
                VisualTransformation.None
            }
            else {
                PasswordVisualTransformation()
            },
            placeholder = { Text("********", color = colorScheme.onSurface) },
            isError = isInCorrect,
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Filled.Password,
                    contentDescription = "Password",
                    tint = colorScheme.onBackground
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
        )

        IconButton(
            onClick = { isVisible = !isVisible },
            modifier = Modifier
                .align(Alignment.CenterEnd)) {
            if (isVisible){
                Icon(Icons.Outlined.LockOpen, contentDescription = "Глаз")
            } else {
                Icon(Icons.Outlined.Lock, contentDescription = "Глаз")
            }
        }
    }
}

@Composable
fun PasswordField(
    keyboardActions: KeyboardActionHandler,
    isInCorrect: Boolean,
    state: TextFieldState,
    enable: Boolean = true
){
    var isVisible by remember { mutableStateOf(false) }

    Box {
        OutlinedSecureTextField(
            state = state,
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
            onKeyboardAction = keyboardActions,
            placeholder = { Text("********", color = colorScheme.onSurface) },
            isError = isInCorrect,
            textObfuscationMode = if (isVisible){
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Password,
                    contentDescription = "Password",
                    tint = colorScheme.onBackground
                )
            },
            modifier = Modifier
                .clip(Shapes.small)
                .fillMaxWidth(),
            enabled = enable
        )

        IconButton(
            onClick = { isVisible = !isVisible },
            modifier = Modifier
                .align(Alignment.CenterEnd)) {
            if (isVisible){
                Icon(Icons.Outlined.LockOpen, contentDescription = "Глаз")
            } else {
                Icon(Icons.Outlined.Lock, contentDescription = "Глаз")
            }
        }
    }
}

@Composable
fun MyTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    keyboardActions: KeyboardActions,
    isInCorrect: Boolean,
    keyboardOptions: KeyboardOptions,
    placeholderText: String,
    icon: ImageVector
){
    OutlinedTextField(
        value = value,
        onValueChange = { onTextChanged(it) },
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorScheme.onBackground,   // Основной цвет для акцентов
            unfocusedBorderColor = colorScheme.onSurface, // Цвет границ для неактивного состояния
            unfocusedPlaceholderColor = colorScheme.onBackground,
            focusedTextColor = colorScheme.onBackground,
            unfocusedTextColor = colorScheme.onBackground,
        ),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        placeholder = { Text(placeholderText, color = colorScheme.onSurface) },
        isError = isInCorrect,
        singleLine = true,
        leadingIcon = {
            Icon(
                icon,
                contentDescription = "",
                tint = colorScheme.onBackground
            )
        },
        modifier = Modifier
            .clip(Shapes.small)
            .fillMaxWidth()
    )
}