package com.example.campaign_master.data.remote.models.loot

data class EquipmentItem(
    val name: String,
    val equipment_category: String,
    val weapon_category: String?, // null if not weapon
    val armor_category: String?,  // null if not armor
    val weight: Double?,
    val cost: Cost,
    val desc: List<String>?,
    val url: String,
)