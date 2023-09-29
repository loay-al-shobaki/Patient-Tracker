package com.example.patienttracker.presentation.patientListScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.patienttracker.presentation.domain.model.Patient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    onFabClicked: () -> Unit,
    onItemClicked: (Int?) -> Unit,
    viewmodel: PatientListViewModel = hiltViewModel()
) {

    val patientList by viewmodel.patientList.collectAsState()
    Scaffold(
        topBar = { ListAppBar() },
        floatingActionButton = {
            ListFloatinActionButton(onFabClicked = onFabClicked)
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(patientList) { patient ->
                PationtItem(
                    patient = patient,
                    onItemClick = { onItemClicked(patient.patienId)

                                  },
                    onDeleteConfirm = { viewmodel.deletePatient(patient) }
                )

            }
        }
        if (patientList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add Patients Detailes\nby pressing add button.",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400
                    ),
                    textAlign = TextAlign.Center
                )

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        title = {
            Text(
                text = "Patient Tracker",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }

    )
}

@Composable
fun ListFloatinActionButton(
    onFabClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = onFabClicked
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add  Patient Button")
    }

}


