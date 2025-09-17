package com.example.campaign_master.features.loot_manager

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.campaign_master.data.remote.Open5eApi

class LootManagerViewModel : ViewModel() {

    private val TAG = "LootManagerVM"

    private val api = Open5eApi

    private val _loot = mutableStateOf<List<Loot>>(emptyList)

    val loot: State<List<Loot>> = _loot

    val isLoading = mutableStateOf(false)

    
}