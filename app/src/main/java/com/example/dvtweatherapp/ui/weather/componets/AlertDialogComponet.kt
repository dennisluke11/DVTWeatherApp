package com.example.dvtweatherapp.ui.weather.componets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertDialogComponet (
    openDialog: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    confirmText: String = "OK"
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = confirmText)
                }
            }
        )
    }
}