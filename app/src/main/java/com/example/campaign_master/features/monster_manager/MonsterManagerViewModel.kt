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

    private suspend fun fetchAllMonsters(query: String): List<Monster> {
        val allMonsters = mutableListOf<Monster>()
        var nextUrl: String? = null
        var currentPage = Open5eApi.service.getMonsters(query)

        allMonsters += currentPage.results
        nextUrl = currentPage.next

        while (nextUrl != null) {
            // Remove base URL to extract just the path/query
            val relativePath = nextUrl.removePrefix("https://api.open5e.com/")
            currentPage = Open5eApi.service.getMonstersByUrl(relativePath)
            allMonsters += currentPage.results
            nextUrl = currentPage.next
        }

        return allMonsters
    }


    // API call to fetch monsters based on the query
    fun searchMonsters(query: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val allMonsters = fetchAllMonsters(query)
                Log.d("MonsterManagerVM", "API returned ${allMonsters.size} total monsters")

                val filtered = allMonsters.map {
                    Log.d("MonsterManagerVM", "Monster: ${it.name}")
                    it
                }.filter { monster ->
                    val crMatch =
                        selectedCR.value == null || monster.challenge_rating == selectedCR.value
                    val typeMatch = selectedType.value == null || monster.type.equals(
                        selectedType.value,
                        ignoreCase = true
                    )

                    Log.d(
                        "MonsterManagerVM",
                        "Monster: ${monster.name}, CR Match: $crMatch, Type Match: $typeMatch"
                    )
                    crMatch && typeMatch
                }

                _monsters.value = filtered
                Log.d("MonsterManagerVM", "Filtered result count: ${filtered.size}")
            } catch (e: Exception) {
                Log.e("MonsterManagerVM", "Failed to fetch monsters: ${e.message}", e)
                _monsters.value = emptyList()
            } finally {
                isLoading.value = false
                Log.d("MonsterManagerVM", "isLoading set to false")
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