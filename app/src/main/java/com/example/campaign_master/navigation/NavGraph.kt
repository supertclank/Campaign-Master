package com.example.campaign_master.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.campaign_master.data.remote.models.Monster
import com.example.campaign_master.features.encounter_builder.EncounterBuilderScreen
import com.example.campaign_master.features.home.HomeScreen
import com.example.campaign_master.features.loot_manager.LootManagerScreen
import com.example.campaign_master.features.monster_manager.MonsterManagerScreen
import com.example.campaign_master.features.notes.NotesJournalScreen
import com.example.campaign_master.features.session_planner.SessionPlannerScreen
import com.example.campaign_master.features.settings.SettingsScreen
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.SessionPlanner.route) {
            SessionPlannerScreen()
        }
        composable(Screen.EncounterBuilder.route) {
            EncounterBuilderScreen()
        }
        composable(Screen.MonsterManager.route) {
            MonsterManagerScreen()
        }
        composable(Screen.NotesJournal.route) {
            NotesJournalScreen()
        }
        composable(Screen.LootManager.route) {
            LootManagerScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(
            route = "monster_detail/{monsterJson}",
            arguments = listOf(navArgument("monsterJson") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("monsterJson")
            val monster = Json.decodeFromString<Monster>(json ?: "")
            MonsterDetailScreen(monster)
        }
    }
}