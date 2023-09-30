package com.example.patienttracker.presentation.patientDetials

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patienttracker.presentation.domain.model.Patient
import com.example.patienttracker.presentation.domain.repository.PatientRepository
import com.example.patienttracker.util.Constants.PATIENT_DETALIS_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val repository: PatientRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(PatientDetailsUiState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPatientId: Int? = null


    init {
        fetchPatientDetails()
        Log.d("loay", "${currentPatientId}: ")
    }

    fun onEvent(event: PatientDetailsEvents) {
        when (event) {
            is PatientDetailsEvents.EnteredAge -> {
                state = state.copy(age = event.age)
            }

            is PatientDetailsEvents.EnteredAssignedDoctor -> {
                state = state.copy(doctorAssigned = event.doctor)
            }

            is PatientDetailsEvents.EnteredName -> {
                state = state.copy(name = event.name)
            }

            is PatientDetailsEvents.EnteredPrescription -> {
                state = state.copy(perscription = event.Presciption)
            }

            PatientDetailsEvents.SaveButton -> {
                viewModelScope.launch {
                    try {
                        deleteOldPatient()
                        savePatient()
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowToast(
                                message = e.message ?: "Couldn't save patient's details."
                            )
                        )
                    }
                }

            }

            PatientDetailsEvents.SelectedFemale -> {
                state = state.copy(gender = 2)
            }

            PatientDetailsEvents.SelectedMale -> {
                state = state.copy(gender = 1)
            }
        }
    }

    private fun savePatient() {
        val age = state.age.toIntOrNull()
        when {
            state.name.isEmpty() -> throw TextFieldException("Please enter name.")
            state.age.isEmpty() -> throw TextFieldException("Please enter age.")
            state.gender == 0 -> throw TextFieldException("Please select gender")
            state.doctorAssigned.isEmpty() -> throw TextFieldException("Please enter doctor assigned.")
            state.perscription.isEmpty() -> throw TextFieldException("Please enter prescription.")
            age == null || age < 0 || age > 100 -> throw TextFieldException("Please enter valid age.")
        }
        val trimmedName = state.name.trim()
        val trimmedDoctorName = state.doctorAssigned.trim()
        viewModelScope.launch {
            repository.addOrUpdatePatient(
                patient = Patient(
                    name = trimmedName,
                    age = state.age,
                    gender = state.gender,
                    doctorAssigned = trimmedDoctorName,
                    prescription = state.perscription
                )
            )
        }
    }

    private fun deleteOldPatient() {
        savedStateHandle.get<Int>(key = PATIENT_DETALIS_KEY)?.let { patientId ->

            viewModelScope.launch {
                val existingPatient = currentPatientId?.let { repository.getPatientById(it) }
g
                if (existingPatient != null) {
                    viewModelScope.launch {
                        repository.deletePatient(existingPatient)
                    }
                }
            }
        }
    }

    private fun fetchPatientDetails() {
        savedStateHandle.get<Int>(key = PATIENT_DETALIS_KEY)?.let { patientId ->
            Log.d("loay2", "${currentPatientId}: ")
            if (patientId != -1) {
                viewModelScope.launch {

                    repository.getPatientById(patientId)?.apply {
                        state = state.copy(
                            name = name,
                            age = age,
                            gender = gender,
                            doctorAssigned = doctorAssigned,
                            perscription = prescription
                        )
                        currentPatientId = patientId
                        Log.d("loay", "${currentPatientId}: ")
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}


class TextFieldException(message: String?) : Exception(message)