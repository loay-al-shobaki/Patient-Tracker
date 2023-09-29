package com.example.patienttracker.presentation.patientDetials

sealed class PatientDetailsEvents {
    data class EnteredName(val name: String) : PatientDetailsEvents()
    data class EnteredAge(val age: String) : PatientDetailsEvents()
    data class EnteredAssignedDoctor(val doctor: String) : PatientDetailsEvents()
    data class EnteredPrescription(val Presciption: String) : PatientDetailsEvents()
    object SelectedMale : PatientDetailsEvents()
    object SelectedFemale : PatientDetailsEvents()
    object SaveButton : PatientDetailsEvents()
}
