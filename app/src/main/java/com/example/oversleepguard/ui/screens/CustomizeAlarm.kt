package com.example.oversleepguard.ui.screens

import android.media.MediaPlayer
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

/**
 * A screen for customizing alarm settings like pre-departure time and sound.
 *
 * @param onNavigateUp Callback to navigate back to the previous screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeAlarm(onNavigateUp: () -> Unit) {
    var preDepartureTime by remember { mutableStateOf(0f) }
    var preDepartureTimeText by remember { mutableStateOf("0") }

    // --- Sound Section State ---
    val context = LocalContext.current
    var soundMenuExpanded by remember { mutableStateOf(false) }

    // TODO: Replace this with your actual list of sound names
    val soundNames = listOf("Default", "Alarm Clock", "Bell", "Chime", "Digital", "Rooster")
    var selectedSound by remember { mutableStateOf(soundNames[0]) }
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    // Clean up the MediaPlayer when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.release()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CUSTOMIZE ALARM",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Pre Departure Section
            Text("Pre Departure", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Slider(
                    value = preDepartureTime,
                    onValueChange = { newValue ->
                        preDepartureTime = newValue
                        preDepartureTimeText = newValue.roundToInt().toString()
                    },
                    valueRange = 0f..60f, // e.g., for a range of 0 to 60 minutes
                    steps = 59, // This creates 60 steps (0, 1, 2, ..., 60)
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = preDepartureTimeText,
                    onValueChange = { newText ->
                        preDepartureTimeText = newText
                        val value = newText.toFloatOrNull()
                        if (value != null && value in 0f..60f) {
                            preDepartureTime = value
                        }
                    },
                    label = { Text("Min") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Sound Section ---
            Text("Sound", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = soundMenuExpanded,
                onExpandedChange = { soundMenuExpanded = !soundMenuExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    value = selectedSound,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = soundMenuExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = soundMenuExpanded,
                    onDismissRequest = { soundMenuExpanded = false }
                ) {
                    soundNames.forEach { soundName ->
                        DropdownMenuItem(
                            text = { Text(soundName) },
                            onClick = {
                                selectedSound = soundName
                                soundMenuExpanded = false

                                // --- Play Sound Preview ---
                                // Stop any currently playing sound
                                mediaPlayer.value?.release()

                                // TODO: This is a demonstration using the default alarm sound.
                                // You should implement logic to map the `soundName` to the correct sound resource.
                                // For example, by using a Map<String, Int> where the Int is the resource ID (R.raw.sound_name).
                                try {
                                    val defaultAlarmUri = Settings.System.DEFAULT_ALARM_ALERT_URI
                                    mediaPlayer.value = MediaPlayer.create(context, defaultAlarmUri).apply {
                                        start()
                                    }
                                } catch (e: Exception) {
                                    // Handle exceptions, e.g., if no default alarm is set
                                    e.printStackTrace()
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            // Save Button
            Button(
                onClick = onNavigateUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("SAVE", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
