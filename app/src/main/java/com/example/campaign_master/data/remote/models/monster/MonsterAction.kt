package com.example.campaign_master.data.remote.models.monster

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MonsterAction(
    val name: String,
    val desc: String,
) : Parcelable