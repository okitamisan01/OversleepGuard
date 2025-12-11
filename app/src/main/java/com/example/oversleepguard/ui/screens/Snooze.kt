package com.example.oversleepguard.ui.screens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Snooze(
    onBack: () -> Unit,
    eventTime: String,
    snoozeTime: Int,

) {
    var time = eventTime
    var snooze = snoozeTime;

    Scaffold(
        containerColor = Color(0xFF6082B6),
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "SNOOZE",
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 80.sp,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Current Time: ",
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,)
            Text(
                eventTime,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
            )

            Spacer(modifier = Modifier.height(150.dp))
            SnoozeTimer(snooze)
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    "Time Left in Snooze: ",
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    fontSize = 60.sp,)
//                Text(
//                    SnoozeTimer(snooze),
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    fontSize = 60.sp,
//                )
//            }
        }
    }
}
@Composable
fun SnoozeTimer(snoozeMinutes: Int) {
    // Total time in seconds
    var timeLeft by remember { mutableStateOf(snoozeMinutes * 60) }

    // Countdown logic
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            kotlinx.coroutines.delay(1000L) // wait 1 second
            timeLeft -= 1
        }
    }

    // Format as MM:SS
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Text(
        "Time Left in Snooze: ",
        fontSize = 40.sp,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.width(20.dp))
    Text(
        formattedTime,
        fontSize = 60.sp,
        textAlign = TextAlign.Center
    )

//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            "Time Left in Snooze: ",
//            fontWeight = FontWeight.Bold,
//            fontSize = 40.sp
//        )
////        Spacer(modifier = Modifier.width(8.dp))
//        Text(
//            formattedTime,
//            fontWeight = FontWeight.Bold,
//            fontSize = 10.sp
//        )
//    }
}