package com.example.oversleepguard

import android.content.Context
import android.location.LocationManager
import android.os.BatteryManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    eventTime: String,
    eventLocation: String,
    preDepartureMinutes: Int,
    onCustomizeClick: () -> Unit,
    onViewEventsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var statusText by remember { mutableStateOf("On time for departure") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F5FF))
            .padding(horizontal = 16.dp)
    ) {
        // Center card
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column {
                HeaderBar()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    NextAlarmSection(
                        statusText = statusText,
                        eventTime = eventTime,
                        eventLocation = eventLocation,
                        preDepartureMinutes = preDepartureMinutes
                    )

                    ActionButtonsRow(
                        onEdit = { statusText = "Editing alarm..." },
                        onSnooze = { statusText = "Alarm snoozed by 5 minutes" }
                    )

                    PrimaryNavButtons(
                        onCustomizeClick = onCustomizeClick,
                        onViewEventsClick = onViewEventsClick,
                        onSettingsClick = onSettingsClick
                    )
                }
            }
        }

        DeviceStatusRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}

@Composable
private fun HeaderBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF2F7BFF),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(vertical = 22.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "OVERSLEEP GUARD",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            letterSpacing = 1.2.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun NextAlarmSection(
    statusText: String,
    eventTime: String,
    eventLocation: String,
    preDepartureMinutes: Int
) {
    Column {
        Text(
            text = "Next Alarm",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF111111)
        )
        Spacer(modifier = Modifier.height(4.dp))

        // 🔹 Using dynamic values now
        Text(text = "Event: $eventTime", fontSize = 14.sp, color = Color(0xFF333333))
        Text(text = "Location: $eventLocation", fontSize = 14.sp, color = Color(0xFF333333))
        Text(
            text = "Pre-departure: $preDepartureMinutes min",
            fontSize = 14.sp,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Status: $statusText",
            fontSize = 14.sp,
            color = Color(0xFF1B4E9B),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ActionButtonsRow(
    onEdit: () -> Unit,
    onSnooze: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onEdit,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2F7BFF),
                contentColor = Color.White
            )
        ) {
            Text(text = "Edit")
        }
        Button(
            onClick = onSnooze,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2F7BFF),
                contentColor = Color.White
            )
        ) {
            Text(text = "Snooze 5 min")
        }
    }
}

@Composable
private fun PrimaryNavButtons(
    onCustomizeClick: () -> Unit,
    onViewEventsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        FullWidthLightButton("Customize Alarm", onCustomizeClick)
        FullWidthLightButton("View Events", onViewEventsClick)
        FullWidthLightButton("Settings", onSettingsClick)
    }
}

@Composable
private fun FullWidthLightButton(
    label: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE3EEFF),
            contentColor = Color(0xFF23407A)
        )
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DeviceStatusRow(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var batteryPct by remember { mutableStateOf<Int?>(null) }
    var gpsOn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        batteryPct = getBatteryPercentage(context)
        gpsOn = isLocationEnabled(context)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "GPS: ${if (gpsOn) "Active" else "Off"}  |  " +
                    "Battery: ${batteryPct?.let { "$it%" } ?: "--"}",
            fontSize = 12.sp,
            color = Color(0xFF313131),
            fontWeight = FontWeight.Medium
        )
    }
}

private fun getBatteryPercentage(context: Context): Int? {
    val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val level = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    return if (level > 0) level else null
}

private fun isLocationEnabled(context: Context): Boolean {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}
