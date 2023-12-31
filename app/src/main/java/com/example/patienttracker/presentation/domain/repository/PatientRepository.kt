package com.example.patienttracker.presentation.domain.repository


import com.example.patienttracker.presentation.domain.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientRepository {

    suspend fun addOrUpdatePatient(patient: Patient)


    suspend fun deletePatient(patient: Patient)


    suspend fun getPatientById(patientId: Int): Patient?


    suspend fun getAllPatient(): Flow<List<Patient>>
}

