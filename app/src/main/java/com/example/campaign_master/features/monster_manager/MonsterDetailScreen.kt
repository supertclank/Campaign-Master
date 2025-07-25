package com.example.campaign_master.features.monster_manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.campaign_master.data.remote.models.Monster

@Composable
fun MonsterDetailScreen(
    monster: Monster,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back")
        }

        Text(
            monster.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))
        Text("CR: ${monster.challenge_rating}", modifier = Modifier.fillMaxWidth())
        Text("Type: ${monster.type}", modifier = Modifier.fillMaxWidth())
        Text("HP: ${monster.hit_points}", modifier = Modifier.fillMaxWidth())
        Text("AC: ${monster.armor_class}", modifier = Modifier.fillMaxWidth())

        val speed = monster.speed
        if (speed.walk != null || speed.fly != null || speed.swim != null || speed.burrow != null || speed.climb != null) {
            Spacer(Modifier.height(8.dp))
            Text("Speed:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())

            speed.walk?.let { Text("• Walk: ${it} ft.", modifier = Modifier.fillMaxWidth()) }
            speed.fly?.let { Text("• Fly: ${it} ft.", modifier = Modifier.fillMaxWidth()) }
            speed.swim?.let { Text("• Swim: ${it} ft.", modifier = Modifier.fillMaxWidth()) }
            speed.burrow?.let { Text("• Burrow: ${it} ft.", modifier = Modifier.fillMaxWidth()) }
            speed.climb?.let { Text("• Climb: ${it} ft.", modifier = Modifier.fillMaxWidth()) }
        }

        Spacer(Modifier.height(8.dp))
        Text("Size: ${monster.size}", modifier = Modifier.fillMaxWidth())
        Text("Alignment: ${monster.alignment}", modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(18.dp))
        Text("Actions", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())

        monster.actions?.forEach { action ->
            Spacer(Modifier.height(14.dp))
            Text("• ${action.name}", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            Text(
                action.desc,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            )
        }

        if (!monster.legendary_actions.isNullOrEmpty()) {
            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Legendary Actions", style = MaterialTheme.typography.titleMedium)

                    monster.legendary_actions.forEach { action ->
                        Spacer(Modifier.height(12.dp))
                        Text("• ${action.name}", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
                        Text(
                            action.desc,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}