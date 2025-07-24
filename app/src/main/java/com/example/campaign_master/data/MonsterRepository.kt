package com.example.campaign_master.data

import com.example.campaign_master.data.remote.models.Monster
import com.google.firebase.firestore.FirebaseFirestore

object MonsterRepository {
    private val db = FirebaseFirestore.getInstance()
    private val monstersCollection = db.collection("monsters")

    fun saveMonster(monster: Monster, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val data = mapOf(
            "name" to monster.name,
            "challenge_rating" to monster.challenge_rating,
            "armor_class" to monster.armor_class,
            "hit_points" to monster.hit_points,
            "speed" to monster.speed,
            "size" to monster.size,
            "alignment" to monster.alignment,
            "type" to monster.type,
            "legendary_actions" to monster.legendary_actions,
            "actions" to monster.actions,
            "timestamp" to System.currentTimeMillis(),
        )

        monstersCollection.add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }

    fun loadSavedMonsters(onSuccess: (List<Monster>) -> Unit, onError: (Exception) -> Unit) {
        monstersCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val monsters = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Monster::class.java)
                }
                onSuccess(monsters)
            }
            .addOnFailureListener { e -> onError(e) }
    }
}