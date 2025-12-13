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
fun alarmEnd(
    onBack: () -> Unit,
    eventTime: Int

    ) {
    var eventTime = eventTime
    var cal = Calendar.getInstance()
    var timeInt = cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE)
    var timeToEvent = timeInt - eventTime




    Scaffold(
        containerColor = Color(0xFF008000),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Text(
                            text = "Home",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Congratulations!!!",
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    lineHeight = 50.sp,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    "ALARM END",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 60.sp,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .background(Color.Gray)
                    .padding(vertical = 30.dp)
            ) {
                Spacer(modifier = Modifier.height(-20.dp))
                Text(
                    text = "Statistics",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 40.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = ("Event start time: " + formatTime(eventTime)),
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    lineHeight = 30.sp,
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = minHourText(timeToEvent),
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    lineHeight = 30.sp,
                    fontSize = 30.sp,
                )
            }
        }
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
@Composable
fun formatTime(eventTime: Int): String {
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
@Composable
fun minHourText(
    timeToEvent: Int
): String {
    return if (timeToEvent > 0) {
        val minutesAgo = kotlin.math.abs(timeToEvent)
        if (minutesAgo > 60) {
            val minutes = minutesAgo % 60
            val hours = minutesAgo / 60
            if (hours > 1) { "It has been $hours hours and $minutes minutes since the event started." }
            else { "It has been $hours hour and $minutes minute since the event started." }
        }
        else { "It has been $minutesAgo minutes since the event started." }
    } else {
        val minutesAgo = kotlin.math.abs(timeToEvent)
        if (minutesAgo > 60) {
            val minutes = minutesAgo % 60
            val hours = minutesAgo / 60
            if (hours > 1) { "The event starts in $hours hours and $minutes minutes" }
            else { "The event starts in $hours hour and $minutes minute" }
        }
        else { "The event starts in $minutesAgo minutes" }
    }
}
