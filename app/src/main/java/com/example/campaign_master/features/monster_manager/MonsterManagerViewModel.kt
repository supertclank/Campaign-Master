package com.example.campaign_master.features.monster_manager

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campaign_master.data.MonsterRepository
import com.example.campaign_master.data.remote.Open5eApi
import com.example.campaign_master.data.remote.models.Monster
import kotlinx.coroutines.launch

class MonsterManagerViewModel : ViewModel() {

    private val TAG = "MonsterManagerVM"

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

    var selectedLegendary = mutableStateOf<Boolean?>(null)

    // Flag to check if the initial monsters have been loaded
    var hasLoadedInitialMonsters by mutableStateOf(false)

    // API call to fetch monsters based on the query
    fun searchMonsters(query: String) {
        viewModelScope.launch {
            Log.d(TAG, "searchMonsters() called with query: '$query'")
            Log.d(TAG, "Selected CR: ${selectedCR.value}, Selected Type: ${selectedType.value}")

            isLoading.value = true
            try {
                val response = Open5eApi.service.getMonsters(query)
                Log.d(TAG, "API returned ${response.results.size} results")

                val filtered = response.results.filter { monster ->
                    val crMatch =
                        selectedCR.value == null || monster.challenge_rating == selectedCR.value

                    val typeMatch = selectedType.value == null || monster.type.equals(
                        selectedType.value,
                        ignoreCase = true
                    )
                    val legendaryMatch = selectedLegendary.value == null || monster.legendary == selectedLegendary.value


                    Log.d(
                        TAG,
                        "Monster: ${monster.name}, CR Match: $crMatch, Type Match: $typeMatch"
                    )

                    crMatch && typeMatch && legendaryMatch
                }

                Log.d(TAG, "Filtered result count: ${filtered.size}")
                _monsters.value = filtered

            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch monsters: ${e.message}", e)
                _monsters.value = emptyList()
            } finally {
                isLoading.value = false
                Log.d(TAG, "isLoading set to false")
            }
        }
    }

    fun loadSavedMonsters(
        onSuccess: (List<Monster>) -> Unit,
        onError: (Exception) -> Unit,
    ) {
        Log.d(TAG, "Loading saved monsters from Firestore")
        MonsterRepository.loadSavedMonsters(
            onSuccess = {
                Log.d(TAG, "Loaded ${it.size} saved monsters")
                onSuccess(it)
            },
            onError = {
                Log.e(TAG, "Failed to load saved monsters: ${it.message}", it)
                onError(it)
            }
        )
    }
}