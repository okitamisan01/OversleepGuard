package com.example.oversleepguard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * The main screen of the application, which serves as a hub
 * to navigate to other feature screens.
 *
 * @param onNavigateToCustomizeLocation Callback to navigate to the Customize Location screen.
 * @param onNavigateToCustomizeAlarm Callback to navigate to the Customize Alarm screen.
 */
@Composable
fun HomeScreen(
    onNavigateToCustomizeLocation: () -> Unit,
    onNavigateToCustomizeAlarm: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onNavigateToCustomizeLocation) {
                Text("Go to Customize Location")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToCustomizeAlarm) {
                Text("Go to Customize Alarm")
            }
        }
    }
}
