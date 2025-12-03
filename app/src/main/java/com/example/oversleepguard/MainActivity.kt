package com.example.oversleepguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oversleepguard.ui.theme.OversleepGuardTheme

enum class Screen {
    HOME,
    CUSTOMIZE,
    EVENTS,
    SETTINGS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

    // 🔹 Dynamic data for the next alarm
    var eventTime by remember { mutableStateOf("1:25 PM") }
    var eventLocation by remember { mutableStateOf("Gore Hall") }
    var preDepartureMinutes by remember { mutableStateOf(10) }

    when (currentScreen) {
        Screen.HOME -> HomeScreen(
            eventTime = eventTime,
            eventLocation = eventLocation,
            preDepartureMinutes = preDepartureMinutes,
            onCustomizeClick = {
                // later: you can update the values here or from the customize screen
                currentScreen = Screen.CUSTOMIZE
            },
            onViewEventsClick = { currentScreen = Screen.EVENTS },
            onSettingsClick = { currentScreen = Screen.SETTINGS }
        )

        Screen.CUSTOMIZE -> SimpleScreen(
            title = "Customize Alarm Screen",
            onBack = { currentScreen = Screen.HOME }
        )

        Screen.EVENTS -> SimpleScreen(
            title = "View Events Screen",
            onBack = { currentScreen = Screen.HOME }
        )

        Screen.SETTINGS -> SimpleScreen(
            title = "Settings Screen",
            onBack = { currentScreen = Screen.HOME }
        )
    }
}

@Composable
fun SimpleScreen(
    title: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "This is a placeholder page.\nYour teammate can put their UI here and later update the home screen data.",
            style = MaterialTheme.typography.bodyMedium
        )
        Button(onClick = onBack) {
            Text("Back to Home")
        }
    }
}
