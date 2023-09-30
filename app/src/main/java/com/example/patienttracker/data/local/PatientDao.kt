package com.example.patienttracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.patienttracker.presentation.domain.model.Patient
import com.example.patienttracker.util.Constants.PATIENT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao{
    @Upsert()
     fun addOrUpdatePatient(patient: Patient)

    @Delete
     fun deletePatient(patient: Patient)

    @Query("SELECT * FROM patientTable WHERE patienId = :patientId")
     fun getPatientById(patientId: Int): Patient?

    @Query("SELECT * FROM patientTable")
     fun getAllPatient(): Flow<List<Patient>>


}