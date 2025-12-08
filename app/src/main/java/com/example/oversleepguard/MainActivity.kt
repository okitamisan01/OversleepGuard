package com.example.oversleepguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oversleepguard.ui.theme.OversleepGuardTheme

enum class Screen {
    HOME,
    VIEW_EVENTS,
    CUSTOMIZE_ALARM,
    CUSTOMIZE_LOCATION
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OversleepGuardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
fun AppRoot() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }

    // Dynamic event data (you can later update this from other screens)
    var eventTime by remember { mutableStateOf("8:00 AM") }
    var eventLocation by remember { mutableStateOf("Gore Hall") }
    var preDepartureMinutes by remember { mutableStateOf(10) }

    when (currentScreen) {
        Screen.HOME -> HomeScreen(
            eventTime = eventTime,
            eventLocation = eventLocation,
            preDepartureMinutes = preDepartureMinutes,
            onViewEventsClick = { currentScreen = Screen.VIEW_EVENTS },
            onCustomizeAlarmClick = { currentScreen = Screen.CUSTOMIZE_ALARM },
            onCustomizeLocationClick = { currentScreen = Screen.CUSTOMIZE_LOCATION }
        )

        Screen.VIEW_EVENTS -> SimpleScreen(
            title = "View Events Page",
            description = "This is the View Events page. Your teammate can replace this with real event UI.",
            onBack = { currentScreen = Screen.HOME }
        )

        Screen.CUSTOMIZE_ALARM -> SimpleScreen(
            title = "Customize Alarm Page",
            description = "This is the Customize Alarm page. Your teammate can build the alarm settings here.",
            onBack = { currentScreen = Screen.HOME }
        )

        Screen.CUSTOMIZE_LOCATION -> SimpleScreen(
            title = "Customize Location Page",
            description = "This is the Customize Location page. Your teammate can add location settings here.",
            onBack = { currentScreen = Screen.HOME }
        )
    }
}

@Composable
fun SimpleScreen(
    title: String,
    description: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back to Home")
        }
    }
}
