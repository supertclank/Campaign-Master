package com.example.campaign_master.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class Monster(
    val name: String,
    val type: String,
    val challenge_rating: String,
    val armor_class: Int,
    val hit_points: Int,
    val actions: List<MonsterAction> = emptyList()
)
