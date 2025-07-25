package com.example.campaign_master.features.monster_manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CRFilterDropdown(
    selected: String?,
    onSelect: (String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val crOptions = listOf(null) + (0..30).map { it.toString() }

    Column(
        modifier = Modifier.padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Filter by CR")

        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .padding(top = 4.dp)
                .width(180.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(selected ?: "Any")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
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