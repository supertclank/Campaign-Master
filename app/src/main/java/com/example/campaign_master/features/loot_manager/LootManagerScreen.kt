package com.example.campaign_master.features.loot_manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LootManagerScree(
    navController: NavController,
    viewModel: LootManagerViewModel = viewModel(),

    ) {
    val lootResults by viewModel.lootResults
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Loot Manager",
            fontSize = 26.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))
    }
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(lootResults) { LootResults ->
                LootResultsCard(LootResults = LootResults)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}