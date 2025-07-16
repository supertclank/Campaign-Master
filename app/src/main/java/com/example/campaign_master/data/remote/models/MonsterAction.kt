package com.example.campaign_master.data.remote.models

import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize

@Parcelize
@kotlinx.serialization.Serializable
data class MonsterAction(
    val name: String,
    val desc: String
) : Parcelable