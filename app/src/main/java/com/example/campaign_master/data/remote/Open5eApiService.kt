package com.example.campaign_master.data.remote

import com.example.campaign_master.data.remote.models.Open5eResponse
import com.example.campaign_master.data.remote.models.loot.EquipmentItem
import com.example.campaign_master.data.remote.models.loot.MagicItem
import com.example.campaign_master.data.remote.models.monster.Monster
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Open5eApiService {

    @GET("monsters/")
    suspend fun getMonsters(@Query("search") name: String? = null): Open5eResponse<Monster>

    @GET("monsters/")
    suspend fun searchMonsters(
        @Query("search") search: String? = null,
        @Query("challenge_rating") cr: String? = null,
        @Query("type") type: String? = null,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): Open5eResponse<Monster>

    @GET
    suspend fun getMonstersByUrl(@Url url: String): Open5eResponse<Monster>

    @GET("magicitems/")
    suspend fun getMagicItems(): Open5eResponse<MagicItem>

    @GET("equipment/")
    suspend fun getEquipment(): Open5eResponse<EquipmentItem>
}