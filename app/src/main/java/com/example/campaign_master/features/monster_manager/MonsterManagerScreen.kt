package com.example.campaign_master.features.monster_manager

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController

private const val TAG = "MonsterManagerScreen"

@Composable
fun MonsterManagerScreen(
    navController: NavHostController,
    viewModel: MonsterManagerViewModel = viewModel(),
) {
    val monsters by viewModel.monsters
    val isLoading by viewModel.isLoading
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (!viewModel.hasLoadedInitialMonsters) {
            viewModel.searchMonsters("")
            viewModel.hasLoadedInitialMonsters = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Monster Manager",
            fontSize = 26.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search monsters") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CRFilterDropdown(
                    selected = viewModel.selectedCR.value,
                    onSelect = { viewModel.selectedCR.value = it }
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TypeFilterDropdown(
                    selected = viewModel.selectedType.value,
                    onSelect = { viewModel.selectedType.value = it }
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    Log.d(TAG, "Search button clicked")
                    viewModel.searchMonsters(searchQuery)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Search")
            }

            Button(
                onClick = {
                    viewModel.loadSavedMonsters(
                        onSuccess = {
                            Toast.makeText(context, "Loaded saved monsters", Toast.LENGTH_SHORT)
                                .show()
                        },
                        onError = {
                            Toast.makeText(context, "Failed to load saved", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Saved Monsters")
            }
        }

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(monsters) { monster ->
                    MonsterCard(monster = monster, navController = navController)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}