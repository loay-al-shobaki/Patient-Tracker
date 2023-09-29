package com.example.patienttracker.presentation.patientListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patienttracker.presentation.domain.model.Patient

@Composable
fun PationtItem(
    patient: Patient,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onDeleteConfirm: () -> Unit
) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        DeleteDialog(
            title = "Delete",
            message = "Are you sure, you want to delete " +
                    "patient \"${patient.name}\" from patients list",
            onDialogDismiss = { showDialog = false },
            onDialogConfirm = {
                onDeleteConfirm()
                showDialog = false
            }

        )
    }



    Card(
        modifier = modifier.clickable {
            onItemClick()
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier =
                Modifier.weight(9f)
            ) {
                Text(
                    text = patient.name,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Assigned to ${patient.doctorAssigned}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { showDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Icon"
                )

            }
        }
    }

}

@Preview()
@Composable
fun preItem() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        PationtItem(patient1, Modifier, {}, {})
    }

}

val patient1 = Patient("John Doe", "30", 1, "Dr. Smith", "Medicine A")