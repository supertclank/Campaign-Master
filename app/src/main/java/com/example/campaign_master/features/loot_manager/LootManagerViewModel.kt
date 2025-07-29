package com.example.campaign_master.features.loot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campaign_master.data.remote.Open5eApi
import com.example.campaign_master.data.remote.models.loot.GeneratedLoot
import com.example.campaign_master.data.remote.models.loot.WeightedLoot
import kotlinx.coroutines.launch

class LootManagerViewModel : ViewModel() {

    private val api = Open5eApi.service

    private val _lootResults = mutableStateOf<List<GeneratedLoot>>(emptyList())
    val lootResults: State<List<GeneratedLoot>> = _lootResults

    private val coinTable = listOf(
        WeightedLoot(GeneratedLoot("Copper Coins", "Currency", value = 100), weight = 50),
        WeightedLoot(GeneratedLoot("Silver Coins", "Currency", value = 500), weight = 30),
        WeightedLoot(GeneratedLoot("Gold Coins", "Currency", value = 1000), weight = 15),
        WeightedLoot(GeneratedLoot("Platinum Coins", "Currency", value = 1000), weight = 5)
    )

    private val potionTable = listOf(
        WeightedLoot(GeneratedLoot("Potion of Healing", "Potion", value = 50), weight = 60),
        WeightedLoot(GeneratedLoot("Potion of Greater Healing", "Potion", value = 100), weight = 25),
        WeightedLoot(GeneratedLoot("Potion of Superior Healing", "Potion", value = 500), weight = 10),
        WeightedLoot(GeneratedLoot("Potion of Invisibility", "Potion", value = 300), weight = 5)
    )

    private fun weightedRandom(items: List<WeightedLoot>): GeneratedLoot {
        val totalWeight = items.sumOf { it.weight }
        val random = (1..totalWeight).random()
        var cumulative = 0
        for (item in items) {
            cumulative += item.weight
            if (random <= cumulative) return item.loot
        }
        return items.first().loot
    }

    private fun formatCurrency(amountInCopper: Int?): String {
        val gp = amountInCopper?.div(100)
        val sp = (amountInCopper?.rem(100))?.div(10)
        val cp = amountInCopper?.rem(10)
        return buildString {
            gp?.let { if (it > 0) append("$gp GP ") }
            sp?.let { if (it > 0) append("$sp SP ") }
            cp?.let { if (it > 0) append("$cp CP") }
        }.trim()
    }

    fun generateRandomLoot(roll: Int) {
        viewModelScope.launch {
            val loot = mutableListOf<GeneratedLoot>()

            val baseCoin = weightedRandom(coinTable)
            val coinAmount = baseCoin.value?.times(((roll * 5) / 10).coerceAtLeast(1))
            loot.add(
                GeneratedLoot(
                    name = formatCurrency(coinAmount),
                    category = "Currency",
                    value = coinAmount
                )
            )

            val potionChance = if (roll >= 15) 60 else 30
            if ((1..100).random() <= potionChance) {
                loot.add(weightedRandom(potionTable))
            }

            val magicChance = when {
                roll == 20 -> 100
                roll >= 15 -> 50
                else -> 20
            }
            if ((1..100).random() <= magicChance) {
                try {
                    val magicItems = api.getMagicItems().results
                    magicItems.randomOrNull()?.let {
                        loot.add(GeneratedLoot(name = it.name, category = it.type))
                    }
                } catch (e: Exception) {
                    loot.add(
                        GeneratedLoot(
                            "Mystery Item",
                            "Magic Item",
                            "Network error fetching magic item"
                        )
                    )
                }
            }
            _lootResults.value = loot
        }
    }
}