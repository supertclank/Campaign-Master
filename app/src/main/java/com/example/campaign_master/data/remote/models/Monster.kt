package com.example.campaign_master.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Monster(
    val name: String,
    val type: String,
    val challenge_rating: String,
    val armor_class: Int,
    val armor_desc: String? = null,
    val hit_points: Int,
    val hit_dice: String? = null,
    val speed: Speed = Speed(),
    val size: String,
    val alignment: String,

    // Ability Scores
    val strength: Int? = null,
    val dexterity: Int? = null,
    val constitution: Int? = null,
    val intelligence: Int? = null,
    val wisdom: Int? = null,
    val charisma: Int? = null,

    // Saving Throws
    val strength_save: Int? = null,
    val dexterity_save: Int? = null,
    val constitution_save: Int? = null,
    val intelligence_save: Int? = null,
    val wisdom_save: Int? = null,
    val charisma_save: Int? = null,

    // Skills
    val skills: Map<String, Double>? = null,

    // Damage Traits
    val damage_immunities: String? = null,
    val damage_resistances: String? = null,
    val damage_vulnerabilities: String? = null,
    val condition_immunities: String? = null,

    // Senses & Languages
    val senses: String? = null,
    val languages: String? = null,

    // Actions
    val actions: List<MonsterAction>? = emptyList(),
    val legendary_actions: List<MonsterAction>? = emptyList(),
    val special_abilities: List<MonsterAction>? = emptyList(),
    val reactions: List<MonsterAction>? = emptyList(),

    // Spellcasting (Optional)
    val spell_list: List<String>? = null
) : Parcelable
