package com.example.campaign_master.data.remote.models.loot

data class GeneratedLoot(
    val name: String,
    val category: String,
    val description: String? = null,
    val quantity: Int = 1,
    val value: Int? = null,
    val currencyString: String? = null
)