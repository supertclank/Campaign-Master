package com.example.campaign_master.features.monster_manager

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campaign_master.data.remote.Open5eApi
import com.example.campaign_master.data.remote.models.Monster
import kotlinx.coroutines.launch

class MonsterManagerViewModel : ViewModel() {

    // State holder for the monster list
    private val _monsters = mutableStateOf<List<Monster>>(emptyList())
    val monsters: State<List<Monster>> = _monsters

    // State holder for loading
    val isLoading = mutableStateOf(false)

    // API call
    fun searchMonsters(query: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = Open5eApi.service.getMonsters(query)
                _monsters.value = response.results
            } catch (e: Exception) {
                // Log or show an error
                _monsters.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }
}
