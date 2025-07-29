package com.example.campaign_master.features.monster_manager

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.campaign_master.data.remote.models.monster.Monster
import com.example.campaign_master.data.remote.models.monster.MonsterRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun MonsterCard(monster: Monster, navController: NavHostController) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val monsterJson = Uri.encode(Json.encodeToString(monster))
                navController.navigate("monster_detail/$monsterJson")
            },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(monster.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("CR: ${monster.challenge_rating} • Type: ${monster.type}")
            Text("HP: ${monster.hit_points} • AC: ${monster.armor_class}")

            Spacer(Modifier.height(12.dp))

            Button(onClick = {
                MonsterRepository.saveMonster(
                    monster,
                    onSuccess = {
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    },
                    onError = { e ->
                        Toast.makeText(context, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }) {
                Text("Save")
            }
        }
    }
}
