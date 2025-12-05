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
import androidx.compose.runtime.*
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
    onViewEventsClick: () -> Unit,
    onCustomizeAlarmClick: () -> Unit,
    onCustomizeLocationClick: () -> Unit
) {
    var statusText by remember { mutableStateOf("On time for departure") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F5FF))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)            // centered card
                .fillMaxWidth()
                .heightIn(min = 650.dp),            // tall card to reduce white space
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column {
                HeaderBar()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NextAlarmSection(
                        statusText = statusText,
                        eventTime = eventTime,
                        eventLocation = eventLocation,
                        preDepartureMinutes = preDepartureMinutes
                    )

                    ActionButtonsSection(
                        onEdit = { statusText = "Editing alarm..." },
                        onSnooze = { statusText = "Alarm snoozed by 5 minutes" }
                    )

                    PrimaryNavButtons(
                        onViewEventsClick = onViewEventsClick,
                        onCustomizeAlarmClick = onCustomizeAlarmClick,
                        onCustomizeLocationClick = onCustomizeLocationClick
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

/* ----------------------- HEADER BAR ----------------------- */

@Composable
private fun HeaderBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF2F7BFF),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(vertical = 48.dp),  // tall blue area
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "OVERSLEEP GUARD",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            letterSpacing = 1.3.sp,
            textAlign = TextAlign.Center
        )
    }
}

/* ----------------------- NEXT ALARM SECTION ----------------------- */

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
            fontSize = 17.sp,
            color = Color(0xFF111111)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Event: $eventTime", fontSize = 15.sp, color = Color(0xFF333333))
        Text(text = "Location: $eventLocation", fontSize = 15.sp, color = Color(0xFF333333))
        Text(
            text = "Pre-departure: $preDepartureMinutes min",
            fontSize = 15.sp,
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

/* ----------------------- EDIT + SNOOZE SECTION ----------------------- */

@Composable
private fun ActionButtonsSection(
    onEdit: () -> Unit,
    onSnooze: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PillButton(
            label = "Edit",
            onClick = onEdit,
            background = Color(0xFF2F7BFF),
            textColor = Color.White
        )
        PillButton(
            label = "Snooze 5 min",
            onClick = onSnooze,
            background = Color(0xFF2F7BFF),
            textColor = Color.White
        )
    }
}

/* ----------------------- 3 MAIN NAV BUTTONS ----------------------- */

@Composable
private fun PrimaryNavButtons(
    onViewEventsClick: () -> Unit,
    onCustomizeAlarmClick: () -> Unit,
    onCustomizeLocationClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // 1. View Events
        PillButton(
            label = "View Events",
            onClick = onViewEventsClick,
            background = Color(0xFFE3EEFF),
            textColor = Color(0xFF23407A)
        )
        // 2. Customize Alarm
        PillButton(
            label = "Customize Alarm",
            onClick = onCustomizeAlarmClick,
            background = Color(0xFFE3EEFF),
            textColor = Color(0xFF23407A)
        )
        // 3. Customize Location
        PillButton(
            label = "Customize Location",
            onClick = onCustomizeLocationClick,
            background = Color(0xFFE3EEFF),
            textColor = Color(0xFF23407A)
        )
    }
}

/* ----------------------- PILL BUTTON ----------------------- */

@Composable
private fun PillButton(
    label: String,
    onClick: () -> Unit,
    background: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = textColor
        ),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}

/* ----------------------- GPS + BATTERY ROW ----------------------- */

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
            text = "GPS: ${if (gpsOn) "Active" else "Off"}  |  Battery: ${batteryPct ?: "--"}%",
            fontSize = 12.sp,
            color = Color(0xFF313131),
            fontWeight = FontWeight.Medium
        )
    }
}

/* ----------------------- HELPERS ----------------------- */

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
