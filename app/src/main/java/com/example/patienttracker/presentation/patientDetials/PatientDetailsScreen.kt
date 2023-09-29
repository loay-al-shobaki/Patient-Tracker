package com.example.patienttracker.presentation.patientDetials

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailesScreen(
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    onBackCliked: () -> Unit,
    onSuccessfullySaving: () -> Unit
) {

    Scaffold(
        topBar = {
            TopBar(onBackClicked = onBackCliked)
        }
    ) {

        val focusRequester = remember { FocusRequester() }

        val context = LocalContext.current

        LaunchedEffect(key1 = Unit) {
            viewModel.eventFlow.collectLatest { event ->
                when(event) {
                    PatientDetailsViewModel.UiEvent.SaveNote -> {
                        onSuccessfullySaving()
                        Toast.makeText(context, "Successfully Save", Toast.LENGTH_SHORT).show()
                    }
                    is PatientDetailsViewModel.UiEvent.ShowToast -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            delay(500)
            focusRequester.requestFocus()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester = focusRequester),
                value = viewModel.state.name,
                onValueChange = { newValue ->
                    viewModel.onEvent(PatientDetailsEvents.EnteredName(name = newValue))
                },
                label = { Text(text = "Name") },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),

                )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = viewModel.state.age,
                    onValueChange = { newValue ->
                        viewModel.onEvent(PatientDetailsEvents.EnteredAge(newValue))
                    },
                    label = { Text(text = "Age") },
                    textStyle = MaterialTheme.typography.bodySmall,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),

                    )
                Spacer(modifier = Modifier.width(10.dp))
                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Male",
                    selected = viewModel.state.gender == 1,
                    onClick = { viewModel.onEvent(PatientDetailsEvents.SelectedMale) }
                )
                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Female",
                    selected = viewModel.state.gender == 2,
                    onClick = { viewModel.onEvent(PatientDetailsEvents.SelectedFemale) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.state.doctorAssigned,
                onValueChange = { newValue ->
                    viewModel.onEvent(PatientDetailsEvents.EnteredAssignedDoctor(newValue))
                },
                label = { Text(text = "Assigned Doctor's Name") },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),

                )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = viewModel.state.perscription,
                onValueChange = { newValue ->
                    viewModel.onEvent(PatientDetailsEvents.EnteredPrescription(newValue))
                },
                label = { Text(text = "Prescription") },
                textStyle = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onEvent(PatientDetailsEvents.SaveButton)

                }
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onBackClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Patient's Details Screen",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClicked
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
private fun RadioGroup(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}