package com.example.campaign_master.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class MonsterAction(
    val name: String,
    val desc: String
)