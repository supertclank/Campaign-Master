package com.example.campaign_master.data.remote.models.loot

data class LootItem(
    val name: String,
    val description: String? = null,
    val quantity: Int = 1,
    val weight: Double? = null,
    val value: Double? = null,
    val category: String,
    val rarity: String? = null,
)
