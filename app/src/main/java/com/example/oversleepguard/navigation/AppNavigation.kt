package com.example.oversleepguard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oversleepguard.HomeScreen
import com.example.oversleepguard.ui.screens.AddEventScreen
import com.example.oversleepguard.ui.screens.CalenderScreen
import com.example.oversleepguard.ui.screens.CustomizeAlarm
import com.example.oversleepguard.ui.screens.CustomizeLocation
import com.example.oversleepguard.ui.screens.SelectLocationScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // State to hold the selected location
    val selectedLocation = remember { mutableStateOf<LatLng?>(null) }
    var alarmLocation by remember { mutableStateOf("Office") }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                // TODO: Pass actual data from a ViewModel
                eventTime = "10:00 AM",
                eventLocation = "Office",
                preDepartureMinutes = 15,
                onViewEventsClick = { navController.navigate("calendar") },
                onCustomizeAlarmClick = { navController.navigate("customize_alarm") },
                onCustomizeLocationClick = { navController.navigate("customize_location") }
            )
        }
        composable("calendar") {
            CalenderScreen(
                onAddEventClick = { navController.navigate("add_event") }
            )
        }
        composable("add_event") {
            AddEventScreen(
                onBack = { navController.navigateUp() }
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
            CustomizeAlarm(
                onBack = { navController.navigateUp() },
                locationValue = alarmLocation,
                onLocationChange = { alarmLocation = it }
            )
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
