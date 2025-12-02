package com.example.oversleepguard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oversleepguard.ui.screens.CustomizeAlarm
import com.example.oversleepguard.ui.screens.CustomizeLocation
import com.example.oversleepguard.ui.screens.HomeScreen
import com.example.oversleepguard.ui.screens.SelectLocationScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // State to hold the selected location
    val selectedLocation = remember { mutableStateOf<LatLng?>(null) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToCustomizeLocation = { navController.navigate("customize_location") },
                onNavigateToCustomizeAlarm = { navController.navigate("customize_alarm") }
            )
        }
        composable("customize_location") {
            CustomizeLocation(
                selectedLocation = selectedLocation.value,
                onNavigateUp = { navController.navigateUp() },
                onNavigateToSelectLocation = { navController.navigate("select_location") }
            )
        }
        composable("customize_alarm") {
            CustomizeAlarm(onNavigateUp = { navController.navigateUp() })
        }
        composable("select_location") {
            SelectLocationScreen(
                onLocationSelected = { location ->
                    selectedLocation.value = location
                    navController.navigateUp()
                }
            )
        }
    }
}
