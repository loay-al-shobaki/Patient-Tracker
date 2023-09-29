package com.example.patienttracker.presentation.domain.model

data class Patient(
    val name: String,
    val age: String,
    val gender: Int,
    val doctorAssigned: String,
    val prescription: String,
    val patienId: Int? = null
)