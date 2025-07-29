package com.example.campaign_master.data.remote.models.loot

data class MagicItem(
    val name: String,
    val desc: String,
    val type: String,
    val rarity: String?,
    val weight: Double?,
    val value: Int?,
    val requires_attunement: Boolean,
    val url: String,
)