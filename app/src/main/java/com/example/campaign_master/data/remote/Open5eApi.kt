package com.example.campaign_master.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType


object Open5eApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open5e.com/")
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .build()

    val service: Open5eApiService = retrofit.create(Open5eApiService::class.java)
}
