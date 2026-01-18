package com.example.coffeevibe.ui.ui.customUi

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun PasswordAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    output: (String) -> Unit
) {
    val passwordState = rememberTextFieldState()

    AlertDialog(
        icon = {
            Icon(Icons.Outlined.Security, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "Введите ваш пароль")
        },
        text = {
            PasswordField(
                keyboardActions = {},
                isInCorrect = false,
                state = passwordState
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    output(passwordState.text.toString().trim())
                    onConfirmation()
                }
            ) {
                Text("Продолжить")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Назад")
            }
        }
    )
}