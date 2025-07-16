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
fun LegendaryFilter(
    selected: Boolean?,
    onSelect: (Boolean?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    val options = listOf(
        null to "Any",
        true to "Legendary",
        false to "Not Legendary"
    )

    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text("Filter by Legendary")
        Button(onClick = { expanded = true }) {
            Text(options.firstOrNull { it.first == selected }?.second ?: "Any")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { (value, label) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onSelect(value)
                        expanded = false
                    }
                )
            }
        }
    }
}