package com.example.oversleepguard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    var locationText by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf(20) }
    var minute by remember { mutableStateOf(0) }
    var isAm by remember { mutableStateOf(false) }
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
            OutlinedTextField(
                value = locationText,
                onValueChange = { locationText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text("Search location") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE6F0FF),
                    focusedContainerColor = Color(0xFFE6F0FF),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
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
                                .clickable {},
                            contentAlignment = Alignment.Center
                        ) {
                            OutlinedTextField(
                                value = hour.toString(),
                                onValueChange = {
                                    it.toIntOrNull()?.let { newHour ->
                                        if (newHour in 1..12) hour = newHour
                                    }
                                },
                                singleLine = true,
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.width(68.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                            OutlinedTextField(
                                value = minute.toString().padStart(2, '0'),
                                onValueChange = {
                                    it.toIntOrNull()?.let { newMinute ->
                                        if (newMinute in 0..59) minute = newMinute
                                    }
                                },
                                singleLine = true,
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.width(68.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                    }
                }
            }
            Text(
                text = "Repeat:",
                fontSize = 28.sp
            )

            ExposedDropdownMenuBox(
                expanded = expandedRepeat,
                onExpandedChange = { expandedRepeat = !expandedRepeat }
            ) {
                OutlinedTextField(
                    value = selectedRepeat,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRepeat)
                    },
                    label = { Text("Select") }
                )

                ExposedDropdownMenu(
                    expanded = expandedRepeat,
                    onDismissRequest = { expandedRepeat = false }
                ) {
                    repeatOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                selectedRepeat = option
                                expandedRepeat = false
                            },
                            text = { Text(option) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0C2D5B))
                        .clickable { onConfirm() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Confirm",
                        tint = Color.White,
                        modifier = Modifier.size(90.dp)
                    )
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
