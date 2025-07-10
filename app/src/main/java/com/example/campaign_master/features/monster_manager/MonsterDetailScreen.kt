package com.example.campaign_master.features.monster_manager

import com.example.campaign_master.data.remote.models.Monster
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MonsterDetailScreen {
    private val _selectedMonster = MutableStateFlow<Monster?>(null)
    val selectedMonster: StateFlow<Monster?> = _selectedMonster
}