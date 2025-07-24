package com.example.campaign_master.features.monster_manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.campaign_master.data.remote.models.Monster

@Composable
fun MonsterDetailScreen(monster: Monster, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) { Text("Back") }

        Spacer(Modifier.height(16.dp))
        Text(monster.name, style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(8.dp))
        Text("CR: ${monster.challenge_rating}")
        Text("Type: ${monster.type}")
        Text("HP: ${monster.hit_points}")
        Text("AC: ${monster.armor_class}")
        val speed = monster.speed
        if (speed.walk != null || speed.fly != null || speed.swim != null || speed.burrow != null || speed.climb != null) {
            Spacer(Modifier.height(8.dp))
            Text("Speed:", style = MaterialTheme.typography.titleMedium)

            speed.walk?.let { Text("• Walk: ${it} ft.") }
            speed.fly?.let { Text("• Fly: ${it} ft.") }
            speed.swim?.let { Text("• Swim: ${it} ft.") }
            speed.burrow?.let { Text("• Burrow: ${it} ft.") }
            speed.climb?.let { Text("• Climb: ${it} ft.") }
        }

        Spacer(Modifier.height(8.dp))
        Text("Size: ${monster.size}")
        Spacer(Modifier.height(8.dp))
        Text("Alignment: ${monster.alignment}")

        Spacer(Modifier.height(18.dp))
        Text("Actions", style = MaterialTheme.typography.titleMedium)

        monster.actions?.forEach { action ->
            Spacer(Modifier.height(14.dp))
            Text("• ${action.name}", fontWeight = FontWeight.Bold)
            Text(action.desc)
        }

        if (!monster.legendary_actions.isNullOrEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text("Legendary Actions", style = MaterialTheme.typography.titleMedium)
            monster.legendary_actions.forEach { action ->
                Spacer(Modifier.height(8.dp))
                Text("• ${action.name}", fontWeight = FontWeight.Bold)
                Text(action.desc)
            }
        }
    }
}