package com.example.patienttracker.presentation.patientDetials

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class PatientDetailsViewModel : ViewModel() {
    var state by mutableStateOf(PatientDetailsUiState())


    fun onEvent(event: PatientDetailsEvents) {
        when (event) {
            is PatientDetailsEvents.EnteredAge -> {
                state = state.copy(age = event.age)
            }

            is PatientDetailsEvents.EnteredAssignedDoctor ->{
                state = state.copy(doctorAssigned = event.doctor)
            }

            is PatientDetailsEvents.EnteredName -> {
                state = state.copy(name = event.name)
            }

            is PatientDetailsEvents.EnteredPrescription -> {
                state = state.copy(perscription = event.Presciption)
            }

            PatientDetailsEvents.SaveButton -> {

            }

            PatientDetailsEvents.SelectedFemale -> {
                state = state.copy(gender = 2)
            }

            PatientDetailsEvents.SelectedMale -> {
                state = state.copy(gender = 1)
            }
        }
    }


}