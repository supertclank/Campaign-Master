package com.example.campaign_master.features.monster_manager

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campaign_master.data.remote.Open5eApi
import com.example.campaign_master.data.remote.models.monster.Monster
import com.example.campaign_master.data.remote.models.monster.MonsterRepository
import kotlinx.coroutines.launch

class MonsterManagerViewModel : ViewModel() {

    private val TAG = "MonsterManagerVM"
    private val api = Open5eApi.service

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


    // Flag to check if the initial monsters have been loaded
    var hasLoadedInitialMonsters by mutableStateOf(false)

    private suspend fun fetchAllMonsters(query: String): List<Monster> {
        val allMonsters = mutableListOf<Monster>()
        var nextUrl: String? = null
        var currentPage = Open5eApi.service.getMonsters(query)

        allMonsters += currentPage.results
        nextUrl = currentPage.next

        while (nextUrl != null) {
            val relativePath = nextUrl.removePrefix("https://api.open5e.com/")
            currentPage = Open5eApi.service.getMonstersByUrl(relativePath)
            allMonsters += currentPage.results
            nextUrl = currentPage.next
        }

        return allMonsters
    }

    private val _uiMessage = mutableStateOf<String?>(null)
    val uiMessage: State<String?> = _uiMessage

    fun clearUiMessage() {
        _uiMessage.value = null
    }


    // API call to fetch monsters based on the query
    fun searchMonsters(query: String? = null) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = api.searchMonsters(
                    search = query?.takeIf { it.isNotBlank() },
                    limit = 3000,
                    offset = 0
                )

                val crFilter = selectedCR.value
                val typeFilter = selectedType.value

                val filtered = response.results.filter { monster ->
                    val crMatch =
                        crFilter?.let { monster.challenge_rating.trim() == it.trim() } ?: true
                    val typeMatch =
                        typeFilter?.let { monster.type.equals(it, ignoreCase = true) } ?: true
                    crMatch && typeMatch
                }

                _monsters.value = filtered

                if (filtered.isEmpty()) {
                    _uiMessage.value = "No monsters found for the selected filters."
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch monsters: ${e.message}", e)
                _monsters.value = emptyList()
                _uiMessage.value = "Failed to fetch monsters: ${e.message}"
            } finally {
                isLoading.value = false
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