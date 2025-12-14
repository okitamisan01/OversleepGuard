package com.example.oversleepguard.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun SelectLocationScreen(onLocationSelected: (LatLng) -> Unit) {
    val tokyoStation = LatLng(35.681236, 139.767125)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tokyoStation, 15f)
    }
    // Add a state for the marker, which the user can drag.
    val markerState = rememberMarkerState(position = tokyoStation)

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            // When the map is clicked, move the marker to that position.
            onMapClick = { markerState.position = it }
        ) {
            Marker(
                state = markerState,
                draggable = true // Allow the user to drag the marker
            )
        }

        Button(
            onClick = { 
                // Pass the final marker position back when the button is clicked.
                onLocationSelected(markerState.position)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Confirm Location")
        }
    }
}
