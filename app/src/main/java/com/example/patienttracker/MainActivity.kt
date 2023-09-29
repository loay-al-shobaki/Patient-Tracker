package com.example.patienttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.patienttracker.navigation.NavGraphSetup
import com.example.patienttracker.presentation.patientDetials.PatientDetailesScreen
import com.example.patienttracker.presentation.patientDetials.PatientDetailsViewModel
import com.example.patienttracker.presentation.theme.PatientTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatientTrackerTheme {
                val navController = rememberNavController()
                NavGraphSetup(navController = navController)
            }
        }
    }
}

