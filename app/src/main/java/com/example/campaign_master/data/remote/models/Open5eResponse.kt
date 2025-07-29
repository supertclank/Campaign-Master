package com.example.campaign_master.data.remote.models

import com.example.campaign_master.data.remote.models.monster.Monster
import kotlinx.serialization.Serializable

@Serializable
data class Open5eResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Monster>,
)