package com.example.campaign_master.features.monster_manager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(monster.name, style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(8.dp))
                Text("CR: ${monster.challenge_rating}")
                Text("Type: ${monster.type}")
                Text("HP: ${monster.hit_points} (${monster.hit_dice ?: "-"})")
                Text("AC: ${monster.armor_class}${monster.armor_desc?.let { " ($it)" } ?: ""}")

                val speed = monster.speed
                if (speed.walk != null || speed.fly != null || speed.swim != null ||
                    speed.burrow != null || speed.climb != null
                ) {
                    Spacer(Modifier.height(8.dp))
                    Text("Speed:", style = MaterialTheme.typography.titleMedium)
                    speed.walk?.let { Text("• Walk: $it ft.") }
                    speed.fly?.let { Text("• Fly: $it ft.") }
                    speed.swim?.let { Text("• Swim: $it ft.") }
                    speed.burrow?.let { Text("• Burrow: $it ft.") }
                    speed.climb?.let { Text("• Climb: $it ft.") }
                }

                Spacer(Modifier.height(8.dp))
                Text("Size: ${monster.size}")
                Text("Alignment: ${monster.alignment}")
            }
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ability Scores", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AbilityScore("STR", monster.strength)
                    AbilityScore("DEX", monster.dexterity)
                    AbilityScore("CON", monster.constitution)
                    AbilityScore("INT", monster.intelligence)
                    AbilityScore("WIS", monster.wisdom)
                    AbilityScore("CHA", monster.charisma)
                }

                Spacer(Modifier.height(12.dp))

                val saves = listOfNotNull(
                    monster.strength_save?.let { "Str +$it" },
                    monster.dexterity_save?.let { "Dex +$it" },
                    monster.constitution_save?.let { "Con +$it" },
                    monster.intelligence_save?.let { "Int +$it" },
                    monster.wisdom_save?.let { "Wis +$it" },
                    monster.charisma_save?.let { "Cha +$it" }
                )
                if (saves.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text("Saving Throws", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(saves.joinToString())
                }

                monster.skills?.takeIf { it.isNotEmpty() }?.let {
                    Spacer(Modifier.height(12.dp))
                    Text("Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    val skills = it.entries.joinToString { entry -> "${entry.key} ${entry.value}" }
                    Text(skills)
                }

                listOf(
                    "Damage Immunities" to monster.damage_immunities,
                    "Damage Resistances" to monster.damage_resistances,
                    "Damage Vulnerabilities" to monster.damage_vulnerabilities,
                    "Condition Immunities" to monster.condition_immunities
                ).forEach { (title, value) ->
                    value?.takeIf { it.isNotBlank() }?.let {
                        Spacer(Modifier.height(12.dp))
                        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(it)
                    }
                }

                monster.senses?.takeIf { it.isNotBlank() }?.let {
                    Spacer(Modifier.height(12.dp))
                    Text("Senses", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(it)
                }
                monster.languages?.takeIf { it.isNotBlank() }?.let {
                    Spacer(Modifier.height(8.dp))
                    Text("Languages", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(it)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        monster.special_abilities?.takeIf { it.isNotEmpty() }?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Special Abilities", style = MaterialTheme.typography.titleLarge)
                    it.forEach { ability ->
                        Spacer(Modifier.height(12.dp))
                        Text("• ${ability.name}", fontWeight = FontWeight.Bold)
                        Text(ability.desc, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Actions", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                monster.actions?.forEach { action ->
                    Spacer(Modifier.height(14.dp))
                    Text("• ${action.name}", fontWeight = FontWeight.Bold)
                    Text(action.desc, modifier = Modifier.padding(top = 2.dp))
                }
            }
        }

        monster.reactions?.takeIf { it.isNotEmpty() }?.let {
            Spacer(Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Reactions", style = MaterialTheme.typography.titleLarge)
                    it.forEach { reaction ->
                        Spacer(Modifier.height(12.dp))
                        Text("• ${reaction.name}", fontWeight = FontWeight.Bold)
                        Text(reaction.desc, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        monster.legendary_actions?.takeIf { it.isNotEmpty() }?.let {
            Spacer(Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Legendary Actions", style = MaterialTheme.typography.titleLarge)
                    it.forEach { action ->
                        Spacer(Modifier.height(12.dp))
                        Text("• ${action.name}", fontWeight = FontWeight.Bold)
                        Text(action.desc, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        monster.spell_list?.takeIf { it.isNotEmpty() }?.let {
            Spacer(Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Spells", style = MaterialTheme.typography.titleLarge)
                    it.forEach { spell ->
                        Spacer(Modifier.height(4.dp))
                        Text("• $spell")
                    }
                }
            }
        }
    }
}

@Composable
private fun AbilityScore(label: String, score: Int?) {
    val mod = score?.let { (it - 10) / 2 }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontWeight = FontWeight.Bold)
        Text("${score ?: "-"} (${if (mod != null && mod >= 0) "+$mod" else mod ?: "-"})")
    }
}
