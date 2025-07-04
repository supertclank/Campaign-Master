package com.example.campaign_master.data.remote

import com.example.campaign_master.data.remote.models.Open5eResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Open5eApiService {
    @GET("monsters/")
    suspend fun getMonsters(@Query("search") name: String? = null): Open5eResponse
}
