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
import com.example.oversleepguard.ui.screens.AddEventScreen
import com.example.oversleepguard.ui.screens.CalenderScreen
import com.example.oversleepguard.ui.theme.OversleepGuardTheme
import com.example.oversleepguard.ui.screens.CustomizeAlarm
import com.example.oversleepguard.ui.screens.Snooze
import com.example.oversleepguard.ui.screens.StillAtHome
import com.example.oversleepguard.ui.screens.gpsChecking

//import com.example.oversleepguard.ui.screens.GPSChecking
import com.example.oversleepguard.ui.screens.alarmEnd
//import com.example.oversleepguard.ui.screens.StillAtHome

enum class Screen {
    HOME,
    VIEW_EVENTS,
    CUSTOMIZE_ALARM,
    CUSTOMIZE_LOCATION,
    ADD_EVENTS,
    SNOOZE,
    STILL_AT_HOME,
    ALARM_END,
    GPS_CHECKING
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
    var eventTimeInt by remember { mutableStateOf(800) }
    var eventLocation by remember { mutableStateOf("Gore Hall") }
    var preDepartureMinutes by remember { mutableStateOf(10) }
    var snoozeTime by remember { mutableStateOf(5) }


    when (currentScreen) {
        Screen.HOME -> HomeScreen(
            eventTime = eventTime,
            eventLocation = eventLocation,
            preDepartureMinutes = preDepartureMinutes,
            onViewEventsClick = { currentScreen = Screen.VIEW_EVENTS },
            onCustomizeAlarmClick = { currentScreen = Screen.CUSTOMIZE_ALARM },
            onCustomizeLocationClick = { currentScreen = Screen.CUSTOMIZE_LOCATION }
        )

        Screen.VIEW_EVENTS -> CalenderScreen(
            onAddEventClick = {currentScreen = Screen.ADD_EVENTS}
        )

        Screen.ADD_EVENTS->AddEventScreen(
            onBack = {currentScreen = Screen.VIEW_EVENTS}
        )

        Screen.CUSTOMIZE_ALARM -> { CustomizeAlarm (
                onBack = { currentScreen = Screen.HOME },
                onLocationChange = {eventLocation = it},
                locationValue = eventLocation
            )
        }

        Screen.CUSTOMIZE_LOCATION -> SimpleScreen(
            title = "Customize Location Page",
            description = "This is the Customize Location page. Your teammate can add location settings here.",
            onBack = { currentScreen = Screen.HOME }
        )
        // ----------------------------------------------------------- Events triggered by alarm going off -----------------------------------------------------------
        Screen.SNOOZE -> Snooze(
            onBack = { currentScreen = Screen.HOME },
            eventTime = eventTime,
            snoozeTime = snoozeTime
        )
        Screen.STILL_AT_HOME -> StillAtHome(
            onBack = { currentScreen = Screen.HOME },
            onSnooze = { currentScreen = Screen.SNOOZE },
            onLocation = { currentScreen = Screen.GPS_CHECKING },
            eventTime = eventTimeInt,
            eventLocation = eventLocation
        )
        Screen.ALARM_END -> alarmEnd(
            onBack = { currentScreen = Screen.HOME },
            eventTime = eventTimeInt
        )
        Screen.GPS_CHECKING -> gpsChecking(
            onBack = { currentScreen = Screen.HOME },
            onLocationFail = { currentScreen = Screen.STILL_AT_HOME },
            onLocationSuccess = { currentScreen = Screen.ALARM_END },
            eventLocation = eventLocation
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
