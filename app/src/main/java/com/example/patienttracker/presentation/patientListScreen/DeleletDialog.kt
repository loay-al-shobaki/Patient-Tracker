package com.example.patienttracker.presentation.patientListScreen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    title: String,
    message: String,
    onDialogDismiss: () -> Unit,
    onDialogConfirm: () -> Unit
) {

    AlertDialog(
        title ={
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall
            )
        },
        onDismissRequest = { onDialogDismiss() },
        confirmButton = {
            TextButton(onClick = onDialogConfirm) {
                Text(text = "yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDialogDismiss) {
                Text(text = "No")
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,


    )
}