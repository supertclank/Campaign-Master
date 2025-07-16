package com.example.campaign_master.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
@Parcelize
data class Monster(
    val name: String,
    val type: String,
    val challenge_rating: String,
    val armor_class: Int,
    val hit_points: Int,
    val speed: Speed = Speed(),
    val size: String,
    val alignment: String,
    val legendary_actions: List<MonsterAction>? = emptyList(),
    val actions: List<MonsterAction> = emptyList(),
    val legendary: Boolean = false
) : Parcelable
