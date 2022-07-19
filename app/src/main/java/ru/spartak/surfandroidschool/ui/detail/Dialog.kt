package ru.spartak.surfandroidschool.ui.detail

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun Dialog(
    showDialogState: Boolean,
    dismissRequest: () -> Unit,
    dismissButtonText: String,
    confirmRequest: () -> Unit,
    confirmButtonText: String,
    text: String
) {
    if (showDialogState) {
        AlertDialog(
            onDismissRequest = dismissRequest,
            confirmButton = {
                TextButton(onClick = confirmRequest)
                { Text(text = confirmButtonText, style = MaterialTheme.typography.button) }
            },
            dismissButton = {
                TextButton(onClick = dismissRequest)
                { Text(text = dismissButtonText, style = MaterialTheme.typography.button) }
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary
                )
            },
            shape = RectangleShape
        )
    }
}