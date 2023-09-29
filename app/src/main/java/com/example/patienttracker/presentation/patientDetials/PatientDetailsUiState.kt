package com.example.patienttracker.presentation.patientDetials

data class PatientDetailsUiState(
    val name: String = "",
    val age: String = "",
    val gender: Int = 0,
    val doctorAssigned: String = "",
    val perscription: String = ""
)