package com.example.campaign_master.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class Open5eResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Monster>,
)