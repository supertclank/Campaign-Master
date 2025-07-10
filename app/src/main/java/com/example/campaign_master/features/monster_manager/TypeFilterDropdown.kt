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
fun TypeFilterDropdown (
    selected: String?,
    onSelect: (String?) -> Unit,
){
    // State for dropdown
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(null,"aberration","beast","celestial","construct","dragon","elemental","fey","fiend","giant","humanoid","monstrosity","ooze","plant","undead")

    // Dropdown content
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text("Filter by Type")
        Button(onClick = { expanded = true }) {
            Text(selected ?: "Any")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type ?: "Any") },
                    onClick = {
                        onSelect(type)
                        expanded = false
                    }
                )
            }
        }
    }

}