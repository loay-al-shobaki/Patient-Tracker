package com.example.patienttracker.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patienttracker.presentation.patientDetials.PatientDetailesScreen
import com.example.patienttracker.presentation.patientListScreen.PatientListScreen
import com.example.patienttracker.util.Constants.PATIENT_DETALIS_KEY

sealed class Screen(val route: String) {
    object PatientList : Screen(route = "patient_list_screen")
    object patientDetails : Screen(
        route = "patient_details_screen?$PATIENT_DETALIS_KEY=" +
                "{$PATIENT_DETALIS_KEY}"
    )

    fun passPatientId(patientId: Int? = null): String {
        return "patient_details_screen?$PATIENT_DETALIS_KEY=$patientId"
    }
}

@Composable
fun NavGraphSetup(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PatientList.route
    ) {
        composable(
            route = Screen.PatientList.route
        ) {
            PatientListScreen(
                onFabClicked = {
                    navController.navigate(Screen.patientDetails.route)
                },
                onItemClicked = {
                    navController.navigate(Screen.patientDetails.passPatientId(it))

                }
            )
        }

        composable(
            route = Screen.patientDetails.route,
            arguments = listOf(navArgument(name = PATIENT_DETALIS_KEY) {
                type = NavType.IntType
                defaultValue = -1

            })
        ) {

            PatientDetailesScreen(
                onBackCliked = { navController.navigateUp() },
                onSuccessfullySaving = {navController.navigateUp() },
            )
        }


    }
}