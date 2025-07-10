package com.example.campaign_master.features.monster_manager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campaign_master.data.MonsterRepository
import com.example.campaign_master.data.remote.Open5eApi
import com.example.campaign_master.data.remote.models.Monster
import kotlinx.coroutines.launch

class MonsterManagerViewModel : ViewModel() {

    // State holder for the monster list
    private val _monsters = mutableStateOf<List<Monster>>(emptyList())
    val monsters: State<List<Monster>> = _monsters

    // State holder for loading
    val isLoading = mutableStateOf(false)

    // State holder for saved monsters
    private val _savedMonsters = mutableStateOf<List<Monster>>(emptyList())
    val savedMonsters: State<List<Monster>> = _savedMonsters

    // State holder for filter query
    var selectedCR = mutableStateOf<String?>(null)
    var selectedType = mutableStateOf<String?>(null)
    // API call to fetch monsters based on the query
    fun searchMonsters(query: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = Open5eApi.service.getMonsters(query)

                val filtered = response.results.filter { monster ->
                    (selectedCR.value == null || monster.challenge_rating == selectedCR.value) &&
                            (selectedType.value == null || monster.type.equals(selectedType.value, ignoreCase = true))
                }

                _monsters.value = filtered
            } catch (e: Exception) {
                _monsters.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }



    // API call to fetch saved monsters
    fun loadSavedMonsters(
        onSuccess: (List<Monster>) -> Unit,
        onError: (Exception) -> Unit,
    ) {
        MonsterRepository.loadSavedMonsters(
            onSuccess = onSuccess,
            onError = onError
        )
    }
}
