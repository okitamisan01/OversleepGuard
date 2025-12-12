package com.example.oversleepguard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun CalendarScreen(
    monthName: String = "January",
    daysInMonth: Int = 31,
    startOffset: Int = 3,
    onAddEventClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Text(
                text = monthName,
                style = MaterialTheme.typography.headlineMedium
            )
            IconButton(onClick = onAddEventClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        }
        CalendarGrid(
            daysInMonth = daysInMonth,
            startOffset = startOffset
        )
    }
}

@Composable
fun CalendarGrid(
    daysInMonth: Int,
    startOffset: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, Color.Black)
    ) {
        val totalCells = daysInMonth + startOffset
        val rows = (totalCells + 6) / 7

        var dayNumber = 1

        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                repeat(7) { colIndex ->
                    val index = rowIndex * 7 + colIndex

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(1.dp, Color.Black) // Thin inner grid lines
                            .padding(start = 6.dp, top = 4.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (index >= startOffset && dayNumber <= daysInMonth) {
                            Text(
                                text = dayNumber.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            dayNumber++
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true, heightDp = 900)
@Composable
fun CalendarScreenPreview() {
    MaterialTheme {
        CalendarScreen()
    }
}

