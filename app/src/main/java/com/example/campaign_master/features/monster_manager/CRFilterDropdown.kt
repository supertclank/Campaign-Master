package com.example.campaign_master.features.monster_manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CRFilterDropdown(
    selected: String?,
    onSelect: (String?) -> Unit,
) {
    // State for dropdown
    var expanded by remember { mutableStateOf(false) }
    val crOptions = arrayOf(
        null,
        "0",
        "1/8",
        "1/4",
        "1/2",
        "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15",
        "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25",
        "26", "27", "28", "29", "30"
    )

    // Dropdown content
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text("Filter by CR")
        Button(onClick = { expanded = true }) {
            Text(selected ?: "Any")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            crOptions.forEach { cr ->
                DropdownMenuItem(
                    text = { Text(cr ?: "Any") },
                    onClick = {
                        onSelect(cr)
                        expanded = false
                    }
                )
            }
        }
    }
}