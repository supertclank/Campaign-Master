package com.example.campaign_master.features.monster_manager

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campaign_master.data.MonsterRepository

private const val TAG = "MonsterManagerScreen"

@Composable
fun MonsterManagerScreen(viewModel: MonsterManagerViewModel = viewModel()) {
    val monsters by viewModel.monsters
    val isLoading by viewModel.isLoading
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Monster Manager",
            fontSize = 24.sp
        )

        CRFilterDropdown(
            selected = viewModel.selectedCR.value,
            onSelect = { viewModel.selectedCR.value = it }
        )

        TypeFilterDropdown(
            selected = viewModel.selectedType.value,
            onSelect = { viewModel.selectedType.value = it }
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search monsters") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Button(
            onClick = {
                Log.d(TAG, "Search button clicked")
                Log.d(TAG, "Query: $searchQuery")
                Log.d(TAG, "Selected CR: ${viewModel.selectedCR.value}")
                Log.d(TAG, "Selected Type: ${viewModel.selectedType.value}")
                viewModel.searchMonsters(searchQuery)
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp, end = 8.dp)
        ) {
            Text("Search")
        }

        Button(
            onClick = {
                Log.d(TAG, "Loading saved monsters")
                viewModel.loadSavedMonsters(
                    onSuccess = {
                        Toast.makeText(context, "Loaded saved monsters", Toast.LENGTH_SHORT).show()
                    },
                    onError = { e ->
                        Toast.makeText(context, "Failed to load saved", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp, end = 8.dp)
        ) {
            Text("Saved Monsters")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        } else {
            if (monsters.isEmpty()) {
                Log.w(TAG, "Monster list is empty after search")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                items(monsters) { monster ->
                    Log.d(TAG, "Rendering monster card: ${monster.name}")

                    Card(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(monster.name, style = MaterialTheme.typography.titleMedium)
                            Text("CR: ${monster.challenge_rating}")
                            Text("Type: ${monster.type}")
                            Text("HP: ${monster.hit_points}, AC: ${monster.armor_class}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    Log.d(TAG, "Saving monster: ${monster.name}")
                                    MonsterRepository.saveMonster(
                                        monster,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Saved",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        onError = { e ->
                                            Toast.makeText(
                                                context,
                                                "Failed: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    )
                                }
                            ) {
                                Text("Save")
                            }
                        }
                    }
                }
            }
        }
    }
}