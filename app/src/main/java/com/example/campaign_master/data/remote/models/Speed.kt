package com.example.campaign_master.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Speed(
    val walk: Int? = null,
    val swim: Int? = null,
    val fly: Int? = null,
    val burrow: Int? = null,
    val climb: Int? = null,
    val hover: Boolean? = null
) : Parcelable
