package com.example.campaign_master.features.loot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LootManagerScreen(viewModel: LootManagerViewModel = viewModel()) {
    val lootResults by viewModel.lootResults
    var playerRoll by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Loot Generator", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(lootResults) { item ->
                Column(Modifier.padding(8.dp)) {
                    Text(
                        "${item.name} (${item.category})",
                        style = MaterialTheme.typography.titleMedium
                    )
                    item.description?.let { Text(it) }
                    Text("Quantity: ${item.quantity}")
                    when {
                        item.currencyString != null -> Text("Value: ${item.currencyString}")
                        item.value != null -> Text("Value: ${item.value} gp")
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = playerRoll,
                onValueChange = { playerRoll = it.filter { ch -> ch.isDigit() } },
                label = { Text("Player Roll") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val roll = playerRoll.toIntOrNull()?.coerceIn(1, 40) ?: 10
                    viewModel.generateRandomLoot(roll)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Generate Loot")
            }
        }
    }
}