package com.example.patienttracker.data.repository

import com.example.patienttracker.data.local.PatientDao
import com.example.patienttracker.presentation.domain.model.Patient
import com.example.patienttracker.presentation.domain.repository.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class PatientRepositoryImpl(
    private val dao: PatientDao
) : PatientRepository {
    override suspend fun addOrUpdatePatient(patient: Patient) {
        withContext(Dispatchers.IO) {
            dao.addOrUpdatePatient(patient)
        }

    }

    override suspend fun deletePatient(patient: Patient) {
        withContext(Dispatchers.IO) {
            dao.deletePatient(patient)
        }
    }

    override suspend fun getPatientById(patientId: Int): Patient? {
        return withContext(Dispatchers.IO) {
            dao.getPatientById(patientId)
        }
    }

    override suspend fun getAllPatient(): Flow<List<Patient>> {
            return dao.getAllPatient()
    }
}