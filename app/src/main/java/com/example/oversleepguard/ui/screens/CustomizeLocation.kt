package com.example.oversleepguard.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeLocation(
    selectedLocation: LatLng?,
    onNavigateUp: () -> Unit,
    onNavigateToSelectLocation: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CUSTOMIZE LOCATION",
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
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // Custom map illustration created with Canvas
            MapIllustration(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Text("EVENTS", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            EventRow(
                label = "EVENT1", 
                location = selectedLocation,
                onSelectClick = onNavigateToSelectLocation
            )
            Spacer(modifier = Modifier.height(16.dp))
            EventRow(label = "EVENT2", location = null, onSelectClick = onNavigateToSelectLocation)
            Spacer(modifier = Modifier.height(16.dp))
            EventRow(label = "EVENT3", location = null, onSelectClick = onNavigateToSelectLocation)

            Spacer(modifier = Modifier.weight(1f))

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

@Composable
private fun EventRow(label: String, location: LatLng?, onSelectClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            label,
            modifier = Modifier.weight(0.4f),
            fontWeight = FontWeight.SemiBold
        )
        
        val locationText = if (location != null) {
            "Lat: ${String.format("%.3f", location.latitude)}, Lng: ${String.format("%.3f", location.longitude)}"
        } else {
            "Not selected"
        }

        Text(
            text = locationText,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )

        Button(onClick = onSelectClick, modifier = Modifier.padding(start = 8.dp)) {
            Text("SELECT")
        }
    }
}

/**
 * A composable that draws a simple, abstract map illustration using basic shapes.
 */
@Composable
fun MapIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) { 
        val roadColor = Color(0xFFFBC02D) // Orange-like color for main roads
        val streetColor = Color(0xFFE0E0E0) // Light gray for smaller streets
        val parkColor = Color(0xFF689F38) // Green for parks
        val waterColor = Color(0xFF03A9F4) // Blue for water

        // Background
        drawRect(Color(0xFFF5F5F5))

        // Water areas
        drawRect(waterColor, topLeft = Offset(size.width * 0.6f, size.height * 0.1f), size = size * 0.2f)
        drawPath(Path().apply{
            moveTo(size.width * 0.2f, size.height)
            lineTo(size.width * 0.4f, size.height * 0.8f)
            lineTo(size.width * 0.5f, size.height)
            close()
        }, color = waterColor)

        // Park areas
        drawCircle(parkColor, radius = size.width * 0.15f, center = Offset(size.width * 0.1f, size.height * 0.2f))
        drawRect(parkColor, topLeft = Offset(size.width * 0.7f, size.height * 0.6f), size = size / 5f)

        // Streets
        for (i in 1..5) {
            val x = size.width * i / 6f
            drawLine(streetColor, start = Offset(x, 0f), end = Offset(x, size.height), strokeWidth = 2f)
        }
         for (i in 1..4) {
            val y = size.height * i / 5f
            drawLine(streetColor, start = Offset(0f, y), end = Offset(size.width, y), strokeWidth = 2f)
        }

        // Main roads
        drawLine(roadColor, start = Offset(size.width * 0.1f, 0f), end = Offset(size.width * 0.9f, size.height), strokeWidth = 8f)
        drawLine(roadColor, start = Offset(0f, size.height * 0.5f), end = Offset(size.width, size.height * 0.4f), strokeWidth = 12f)
    }
}