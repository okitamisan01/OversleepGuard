package com.example.oversleepguard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    // State
    var locationText by remember { mutableStateOf("") }

    // time state
    var hour by remember { mutableStateOf(20) }      // 0..23 or 1..12 depending on UI
    var minute by remember { mutableStateOf(0) }     // 0..59
    var isAm by remember { mutableStateOf(false) }   // false -> PM in screenshot (20:00)

    // repeat state
    val repeatOptions = listOf("Never", "Daily", "Weekly", "Monthly")
    var expandedRepeat by remember { mutableStateOf(false) }
    var selectedRepeat by remember { mutableStateOf("Select") }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 12.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 22.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Text(
                text = "Location:",
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFE6F0FF))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "menu"
                    )


                    Text(
                        text = if (locationText.isBlank()) "Search location" else locationText,
                        modifier = Modifier.weight(1f).padding(start = 12.dp),
                        textAlign = TextAlign.Center
                    )

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search"
                    )
                }
            }
            Text(
                text = "Time:",
                fontSize = 28.sp
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDDEEFF))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(68.dp)
                                .height(68.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .clickable {
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = String.format("%02d", hour),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(":", fontSize = 32.sp, modifier = Modifier.padding(top = 6.dp))
                        Box(
                            modifier = Modifier
                                .width(68.dp)
                                .height(68.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = String.format("%02d", minute),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val amBg = if (isAm) Color(0xFFE7E7E7) else Color.White
                            val pmBg = if (!isAm) Color(0xFFD0D7E6) else Color.White
                            Box(
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(amBg)
                                    .clickable { isAm = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("AM", fontSize = 12.sp)
                            }
                            Box(
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(pmBg)
                                    .clickable { isAm = false },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("PM", fontSize = 12.sp)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Column {
                                Text("Hour", fontSize = 12.sp)
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            TextButton(onClick = {
                            }) {
                                Text("Cancel")
                            }
                            TextButton(onClick = {
                            }) {
                                Text("OK")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
fun AddEventScreenPreview() {
    MaterialTheme {
        AddEventScreen()
    }
}

