package com.example.campaign_master.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBottomSheet(onNavigate: (String) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = { onNavigate(Screen.Home.route) }, // Or use a separate dismiss flag
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Navigate to:", style = MaterialTheme.typography.titleLarge)

            NavigationItem("🏠 Home", Screen.Home.route, onNavigate)
            NavigationItem("📅 Session Planner", Screen.SessionPlanner.route, onNavigate)
            NavigationItem("⚔️ Encounter Builder", Screen.EncounterBuilder.route, onNavigate)
            NavigationItem("👹 Monster Manager", Screen.MonsterManager.route, onNavigate)
            NavigationItem("📓 Notes & Journal", Screen.NotesJournal.route, onNavigate)
            NavigationItem("🎁 Loot Manager", Screen.LootManager.route, onNavigate)
            NavigationItem("⚙️ Settings", Screen.Settings.route, onNavigate)
        }
    }
}

@Composable
fun NavigationItem(label: String, route: String, onNavigate: (String) -> Unit) {
    Button(
        onClick = { onNavigate(route) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label)
    }
}