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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun gpsChecking(
    onBack: () -> Unit,
    eventLocation: String,
    onLocationFail: () -> Unit,
    onLocationSuccess: () -> Unit,
    ) {


    Scaffold(
        containerColor = Color.White,
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
            Spacer(modifier = Modifier.height(-10.dp))
            Text(
                "Verifying Location",
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 45.sp,
            )
            Spacer(modifier = Modifier.height(150.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(0.66f)
                        .aspectRatio(1f),
                    strokeWidth = 8.dp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(120.dp))
            Text(
                text = currentLocation(),
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Location of Event: $eventLocation",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,)
            Spacer(modifier = Modifier.height(30.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Dev Util: ",
                    modifier = Modifier,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.width(5.dp))
                PillButton(
                    label = "At Location",
                    onClick = onLocationSuccess,
                    background = Color(0xFFE3EEFF),
                    textColor = Color(0xFF23407A)
                )
                Spacer(modifier = Modifier.width(15.dp))
                PillButton(
                    label = "Not At Location",
                    onClick = onLocationFail,
                    background = Color(0xFFE3EEFF),
                    textColor = Color(0xFF23407A)
                )
            }
        }
    }
}
// ----------------------------- input API for finding location of user ---------------------------------
@Composable
fun currentLocation(): String {
    "..."
    return "Your Location: ..."
}

// -----------------Pill Button--------------------------------------
@Composable
private fun PillButton(
    label: String,
    onClick: () -> Unit,
    background: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = textColor
        ),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}