package com.example.campaign_master.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object SessionPlanner : Screen("session_planner")
    object EncounterBuilder : Screen("encounter_builder")
    object MonsterManager : Screen("monster_manager")
    object NotesJournal : Screen("notes_journal")
    object LootManager : Screen("loot_manager")
    object Settings : Screen("settings")
}