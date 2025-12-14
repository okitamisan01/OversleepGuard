package com.example.oversleepguard.ui.screens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StillAtHome(
    onBack: () -> Unit,
    onSnooze: () -> Unit,
    onLocation: () -> Unit,
    eventTime: Int,
    eventLocation: String,

    ) {
    var eventTime = eventTime
    var cal = Calendar.getInstance()
    var timeInt = cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE)
    var timeToEvent = timeInt - eventTime

    // Track whether the user already pressed snooze
    var snoozed by remember { mutableStateOf(false) }
    var secondsLeft by remember { mutableStateOf(60) }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0 && !snoozed) {
            delay(1000)
            secondsLeft--
        }
        if (!snoozed) {
            onSnooze()
            snoozed = true
        }
    }



    Scaffold(
        containerColor = Color(0xFFDC143C),
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "",
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 60.sp,

                        )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", )
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
            Spacer(modifier = Modifier.height(0.dp))
            Text(
                text = eventStatusText(eventTime),
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF),
                lineHeight = 50.sp,
                fontSize = 50.sp,
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                "Time of Alarm: ",
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF),
                fontSize = 40.sp,)
            Text(
                text = formatEventTime(eventTime),
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF),
                fontSize = 40.sp,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = eventStatusSubText(timeToEvent, eventLocation),
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.ExtraLight,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF),
                lineHeight = 30.sp,
                fontSize = 30.sp,)

            Spacer(modifier = Modifier.height(150.dp))

            PillButton(
                label = "SNOOZE ($secondsLeft)",
                onClick = {
                    if (!snoozed) {
                        onSnooze()
                        snoozed = true
                    }
                },
                background = Color(0xFFE3EEFF),
                textColor = Color(0xFF23407A)
            )
            PillButton(
                label = "CHECK LOCATION",
                onClick = onLocation,
                background = Color(0xFFE3EEFF),
                textColor = Color(0xFF23407A)
            )
        }
    }
}


fun eventStatusSubText(
    timeToEvent: Int,
    eventLocation: String
): String {
    return if (timeToEvent > 0) {
        "Event started $timeToEvent minutes ago in $eventLocation"
    } else {
        val minutesAgo = kotlin.math.abs(timeToEvent)
        if (minutesAgo > 60) {
            val minutes = minutesAgo % 60
            val hours = minutesAgo / 60
            if (hours > 1) { "Event starts in $hours hours and $minutes minutes in $eventLocation" }
            else { "Event starts in $hours hour and $minutes minutes in $eventLocation" }
        }
        else { "Event starts in $minutesAgo minutes in $eventLocation" }
    }
}

fun eventStatusText(
    timeToEvent: Int
): String {
    return if (timeToEvent < 0) {
        "STILL AT HOME"
    } else {
        "PREPARE TO DEPART"
    }
}


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

fun formatEventTime(eventTime: Int): String {
    val hour24 = eventTime / 100
    val minute = eventTime % 100

    if (minute !in 0..59 || hour24 !in 0..23) {
        return "Invalid time"
    }

    val isAm = hour24 < 12
    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }

    val minuteStr = minute.toString().padStart(2, '0')
    val amPm = if (isAm) "AM" else "PM"

    return "$hour12:$minuteStr $amPm"
}
